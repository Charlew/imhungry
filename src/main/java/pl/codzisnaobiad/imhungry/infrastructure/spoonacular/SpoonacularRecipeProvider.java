package pl.codzisnaobiad.imhungry.infrastructure.spoonacular;

import pl.codzisnaobiad.imhungry.api.request.RecipeRequestModel;
import pl.codzisnaobiad.imhungry.api.response.Ingredient;
import pl.codzisnaobiad.imhungry.api.response.FakeSearchRecipeResponse;
import pl.codzisnaobiad.imhungry.api.response.SearchRecipeResponse;
import pl.codzisnaobiad.imhungry.api.response.SearchRecipesResponse;
import pl.codzisnaobiad.imhungry.api.response.RecipeIngredientsResponse;
import pl.codzisnaobiad.imhungry.domain.RecipeProvider;

import java.util.List;

import static java.util.stream.Collectors.toList;

class SpoonacularRecipeProvider implements RecipeProvider {
    private final SpoonacularClient spoonacularClient;
    private final QuotaPointsCounter quotaPointsCounter;
    private final NutrientsPicker nutrientsPicker;
    private final UrlGenerator urlGenerator;
    private final int quotaPointsLimit;

    SpoonacularRecipeProvider(SpoonacularClient spoonacularClient,
                              QuotaPointsCounter quotaPointsCounter,
                              NutrientsPicker nutrientsPicker,
                              UrlGenerator urlGenerator,
                              int quotaPointsLimit) {
        this.spoonacularClient = spoonacularClient;
        this.quotaPointsCounter = quotaPointsCounter;
        this.nutrientsPicker = nutrientsPicker;
        this.urlGenerator = urlGenerator;
        this.quotaPointsLimit = quotaPointsLimit;
    }

    @Override
    public SearchRecipesResponse searchRecipes(RecipeRequestModel recipeRequestModel) {
        if (quotaPointsCounter.getPointsCount() >= quotaPointsLimit) {
            return new SearchRecipesResponse(List.of(new FakeSearchRecipeResponse()));
        }
        var recipes = spoonacularClient.searchRecipes(recipeRequestModel).getResults()
            .stream()
            .map(this::mapToSearchRecipeResponse)
            .collect(toList());
        return new SearchRecipesResponse(recipes);
    }

    @Override
    public RecipeIngredientsResponse getRecipeIngredients(String id) {
        var recipeInformation = spoonacularClient.getRecipeInformationById(id);
        return prepareRecipeInformationResponse(recipeInformation);
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
            .withImageUrl(urlGenerator.generateImageUrl(extendedIngredient.getImage()))
            .withName(extendedIngredient.getName())
            .withUnit(extendedIngredient.getUnit())
            .build();
    }

    private SearchRecipeResponse mapToSearchRecipeResponse(SpoonacularSearchRecipesResponse.SpoonacularSearchRecipeResponse recipe) {
        return new SearchRecipeResponse(recipe.getId(), recipe.getTitle(), recipe.getImage());
    }

}
