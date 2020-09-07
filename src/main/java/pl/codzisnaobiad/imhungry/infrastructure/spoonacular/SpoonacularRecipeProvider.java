package pl.codzisnaobiad.imhungry.infrastructure.spoonacular;

import pl.codzisnaobiad.imhungry.api.request.RecipeRequestModel;
import pl.codzisnaobiad.imhungry.api.response.Equipment;
import pl.codzisnaobiad.imhungry.api.response.Ingredient;
import pl.codzisnaobiad.imhungry.api.response.Length;
import pl.codzisnaobiad.imhungry.api.response.RecipeIngredientsResponse;
import pl.codzisnaobiad.imhungry.api.response.RecipeInstructionResponse;
import pl.codzisnaobiad.imhungry.api.response.RecipeInstructionsResponse;
import pl.codzisnaobiad.imhungry.api.response.SearchRecipeResponse;
import pl.codzisnaobiad.imhungry.api.response.SearchRecipesResponse;
import pl.codzisnaobiad.imhungry.api.response.Step;
import pl.codzisnaobiad.imhungry.api.response.Temperature;
import pl.codzisnaobiad.imhungry.domain.RecipeProvider;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

class SpoonacularRecipeProvider implements RecipeProvider {
    private final RecipeProvider fakeRecipeProvider;
    private final SpoonacularClient spoonacularClient;
    private final QuotaPointsCounter quotaPointsCounter;
    private final NutrientsPicker nutrientsPicker;
    private final UrlGenerator urlGenerator;

    SpoonacularRecipeProvider(RecipeProvider fakeRecipeProvider,
                              SpoonacularClient spoonacularClient,
                              QuotaPointsCounter quotaPointsCounter,
                              NutrientsPicker nutrientsPicker,
                              UrlGenerator urlGenerator
    ) {
        this.fakeRecipeProvider = fakeRecipeProvider;
        this.spoonacularClient = spoonacularClient;
        this.quotaPointsCounter = quotaPointsCounter;
        this.nutrientsPicker = nutrientsPicker;
        this.urlGenerator = urlGenerator;
    }

    @Override
    public SearchRecipesResponse searchRecipes(RecipeRequestModel recipeRequestModel) {
        if (quotaPointsCounter.quotaPointsExceeded()) {
            return fakeRecipeProvider.searchRecipes(recipeRequestModel);
        }
        var recipes = spoonacularClient.searchRecipes(recipeRequestModel).getResults()
            .stream()
            .map(this::mapToSearchRecipeResponse)
            .collect(toList());
        return new SearchRecipesResponse(recipes);
    }

    @Override
    public RecipeIngredientsResponse getRecipeIngredients(String id) {
        if (quotaPointsCounter.quotaPointsExceeded()) {
            return fakeRecipeProvider.getRecipeIngredients(id);
        }
        var recipeInformation = spoonacularClient.getRecipeInformationById(id);
        return prepareRecipeInformationResponse(recipeInformation);
    }

    @Override
    public RecipeInstructionsResponse getRecipeInstructions(String recipeId) {
        if (quotaPointsCounter.quotaPointsExceeded()) {
            return fakeRecipeProvider.getRecipeInstructions(recipeId);
        }
        var recipeInstructions = spoonacularClient.getRecipeInstructions(recipeId);
        return new RecipeInstructionsResponse(prepareRecipeInstruction(recipeInstructions));
    }

    private List<RecipeInstructionResponse> prepareRecipeInstruction(SpoonacularGetAnalyzedInstructionsResponse[] recipeInstructions) {
        return stream(recipeInstructions)
            .map(this::mapToRecipeInstruction)
            .collect(toList());
    }

    private RecipeInstructionResponse mapToRecipeInstruction(SpoonacularGetAnalyzedInstructionsResponse instruction) {
        return new RecipeInstructionResponse(instruction.getName(), prepareSteps(instruction));
    }

    private List<Step> prepareSteps(SpoonacularGetAnalyzedInstructionsResponse instruction) {
        return instruction.getSteps()
            .stream()
            .map(this::mapToStep)
            .collect(toList());
    }

    private Step mapToStep(SpoonacularGetAnalyzedInstructionsResponse.Step step) {
        return Step.newBuilder()
            .withNumber(step.getNumber())
            .withStep(step.getStep())
            .withEquipment(prepareEquipment(step.getEquipment()))
            .withIngredients(prepareIngredientsForStep(step.getIngredients()))
            .withLength(mapToLength(step.getLength()))
            .build();
    }

    private List<Ingredient> prepareIngredientsForStep(List<SpoonacularGetAnalyzedInstructionsResponse.Step.Ingredient> ingredients) {
        return ingredients.stream()
            .map(this::mapToIngredient)
            .collect(toList());
    }

    private Ingredient mapToIngredient(SpoonacularGetAnalyzedInstructionsResponse.Step.Ingredient ingredient) {
        return Ingredient.newBuilder()
            .withName(ingredient.getName())
            .withImage(urlGenerator.generateImageUrl(ingredient.getImage()))
            .build();
    }

    private List<Equipment> prepareEquipment(List<SpoonacularGetAnalyzedInstructionsResponse.Step.Equipment> equipment) {
        return equipment == null ? null : equipment.stream()
            .map(this::mapToEquipment)
            .collect(toList());
    }

    private Equipment mapToEquipment(SpoonacularGetAnalyzedInstructionsResponse.Step.Equipment equipment) {
        return new Equipment(
            equipment.getName(),
            urlGenerator.generateImageUrl(equipment.getImage()),
            mapToTemperature(equipment.getTemperature())
        );
    }

    private Temperature mapToTemperature(SpoonacularGetAnalyzedInstructionsResponse.Step.Equipment.Temperature temperature) {
        return temperature == null ? null : new Temperature(temperature.getNumber(), temperature.getUnit());
    }

    private Length mapToLength(SpoonacularGetAnalyzedInstructionsResponse.Step.Length length) {
        return length == null ? null : new Length(length.getNumber(), length.getUnit());
    }

    private RecipeIngredientsResponse prepareRecipeInformationResponse(SpoonacularGetRecipeInformationResponse recipeInformation) {
        return RecipeIngredientsResponse.newBuilder()
            .withIngredients(prepareIngredients(recipeInformation.getExtendedIngredients()))
            .withNutrients(nutrientsPicker.pickSupportedNutrients(recipeInformation.getNutrition().getNutrients()))
            .withReadyInMinutes(recipeInformation.getReadyInMinutes())
            .withServings(recipeInformation.getServings())
            .build();
    }

    private List<Ingredient> prepareIngredients(
        List<SpoonacularGetRecipeInformationResponse.ExtendedIngredient> extendedIngredients
    ) {
        return extendedIngredients.stream()
            .map(this::mapToIngredient)
            .collect(toList());
    }

    private Ingredient mapToIngredient(
        SpoonacularGetRecipeInformationResponse.ExtendedIngredient extendedIngredient
    ) {
        return Ingredient.newBuilder()
            .withId(extendedIngredient.getId())
            .withAmount(extendedIngredient.getAmount())
            .withImage(urlGenerator.generateImageUrl(extendedIngredient.getImage()))
            .withName(extendedIngredient.getName())
            .withUnit(extendedIngredient.getUnit())
            .build();
    }

    private SearchRecipeResponse mapToSearchRecipeResponse(SpoonacularSearchRecipesResponse.SpoonacularSearchRecipeResponse recipe) {
        return new SearchRecipeResponse(recipe.getId(), recipe.getTitle(), recipe.getImage());
    }

}
