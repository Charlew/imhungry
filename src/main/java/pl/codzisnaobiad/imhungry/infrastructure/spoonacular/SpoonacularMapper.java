package pl.codzisnaobiad.imhungry.infrastructure.spoonacular;

import org.springframework.stereotype.Component;
import pl.codzisnaobiad.imhungry.api.response.recipe.Equipment;
import pl.codzisnaobiad.imhungry.api.response.recipe.Ingredient;
import pl.codzisnaobiad.imhungry.api.response.recipe.Length;
import pl.codzisnaobiad.imhungry.api.response.recipe.RecipeIngredientsResponse;
import pl.codzisnaobiad.imhungry.api.response.recipe.RecipeInstructionResponse;
import pl.codzisnaobiad.imhungry.api.response.recipe.RecipeInstructionsResponse;
import pl.codzisnaobiad.imhungry.api.response.recipe.SearchRecipeResponse;
import pl.codzisnaobiad.imhungry.api.response.recipe.SearchRecipesResponse;
import pl.codzisnaobiad.imhungry.api.response.recipe.Step;
import pl.codzisnaobiad.imhungry.api.response.recipe.Temperature;

import java.util.List;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

@Component
class SpoonacularMapper {
    private final NutrientsPicker nutrientsPicker;
    private final UrlGenerator urlGenerator;

    SpoonacularMapper(NutrientsPicker nutrientsPicker, UrlGenerator urlGenerator) {
        this.nutrientsPicker = nutrientsPicker;
        this.urlGenerator = urlGenerator;
    }

    SearchRecipesResponse prepareSearchRecipesResponse(SpoonacularSearchRecipesResponse spoonacularSearchRecipesResponse) {
        var recipes = spoonacularSearchRecipesResponse.getResults().stream()
            .map(this::mapToSearchRecipeResponse)
            .collect(toList());

        return new SearchRecipesResponse(recipes);
    }

    RecipeIngredientsResponse prepareRecipeIngredientsResponse(SpoonacularRecipeInformationResponse spoonacularRecipeInformationResponse) {
        return RecipeIngredientsResponse.newBuilder()
            .withIngredients(prepareIngredients(spoonacularRecipeInformationResponse.getExtendedIngredients()))
            .withNutrients(nutrientsPicker.pickSupportedNutrients(spoonacularRecipeInformationResponse.getNutrition().getNutrients()))
            .withReadyInMinutes(spoonacularRecipeInformationResponse.getReadyInMinutes())
            .withServings(spoonacularRecipeInformationResponse.getServings())
            .build();
    }

    RecipeInstructionsResponse prepareRecipeInstructionsResponse(SpoonacularAnalyzedInstructionsResponse[] spoonacularRecipeInstructionsResponse) {
        var instructions = stream(spoonacularRecipeInstructionsResponse)
            .map(this::mapToRecipeInstruction)
            .collect(toList());

        return new RecipeInstructionsResponse(instructions);
    }

    private SearchRecipeResponse mapToSearchRecipeResponse(SpoonacularSearchRecipesResponse.SpoonacularSearchRecipeResponse recipe) {
        return new SearchRecipeResponse(recipe.getId(), recipe.getTitle(), recipe.getImage());
    }

    private List<Ingredient> prepareIngredients(
        List<SpoonacularRecipeInformationResponse.ExtendedIngredient> extendedIngredients
    ) {
        return extendedIngredients.stream()
            .map(this::mapToIngredient)
            .collect(toList());
    }

    private Ingredient mapToIngredient(
        SpoonacularRecipeInformationResponse.ExtendedIngredient extendedIngredient
    ) {
        return Ingredient.newBuilder()
            .withId(extendedIngredient.getId())
            .withAmount(extendedIngredient.getAmount())
            .withImage(urlGenerator.generateIngredientImageUrl(extendedIngredient.getImage()))
            .withName(extendedIngredient.getName())
            .withUnit(extendedIngredient.getUnit())
            .build();
    }

    private RecipeInstructionResponse mapToRecipeInstruction(SpoonacularAnalyzedInstructionsResponse instruction) {
        return new RecipeInstructionResponse(instruction.getName(), prepareSteps(instruction));
    }

    private List<Step> prepareSteps(SpoonacularAnalyzedInstructionsResponse instruction) {
        return instruction.getSteps()
            .stream()
            .map(this::mapToStep)
            .collect(toList());
    }

    private Step mapToStep(SpoonacularAnalyzedInstructionsResponse.Step step) {
        return Step.newBuilder()
            .withNumber(step.getNumber())
            .withStep(step.getStep())
            .withEquipment(prepareEquipment(step.getEquipment()))
            .withLength(mapToLength(step.getLength()))
            .build();
    }

    private List<Equipment> prepareEquipment(List<SpoonacularAnalyzedInstructionsResponse.Step.Equipment> equipment) {
        return equipment == null ? null : equipment.stream()
            .map(this::mapToEquipment)
            .collect(toList());
    }

    private Equipment mapToEquipment(SpoonacularAnalyzedInstructionsResponse.Step.Equipment equipment) {
        return new Equipment(
            equipment.getName(),
            urlGenerator.generateEquipmentImageUrl(equipment.getImage()),
            mapToTemperature(equipment.getTemperature())
        );
    }

    private Temperature mapToTemperature(SpoonacularAnalyzedInstructionsResponse.Step.Equipment.Temperature temperature) {
        return temperature == null ? null : new Temperature(temperature.getNumber(), temperature.getUnit());
    }

    private Length mapToLength(SpoonacularAnalyzedInstructionsResponse.Step.Length length) {
        return length == null ? null : new Length(length.getNumber(), length.getUnit());
    }

}
