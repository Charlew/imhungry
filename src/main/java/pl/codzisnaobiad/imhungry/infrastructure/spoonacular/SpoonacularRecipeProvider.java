package pl.codzisnaobiad.imhungry.infrastructure.spoonacular;

import pl.codzisnaobiad.imhungry.api.request.RecipeRequestModel;
import pl.codzisnaobiad.imhungry.api.response.Ingredient;
import pl.codzisnaobiad.imhungry.api.response.FakeSearchRecipeResponse;
import pl.codzisnaobiad.imhungry.api.response.Nutrient;
import pl.codzisnaobiad.imhungry.api.response.SearchRecipeResponse;
import pl.codzisnaobiad.imhungry.api.response.SearchRecipesResponse;
import pl.codzisnaobiad.imhungry.api.response.RecipeIngredientsResponse;
import pl.codzisnaobiad.imhungry.domain.RecipeProvider;

import java.util.List;

import static java.util.stream.Collectors.toList;

class SpoonacularRecipeProvider implements RecipeProvider {
    private final SpoonacularClient spoonacularClient;
    private final QuotaPointsCounter quotaPointsCounter;
    private final int quotaPointsLimit;

    SpoonacularRecipeProvider(SpoonacularClient spoonacularClient,
                              QuotaPointsCounter quotaPointsCounter,
                              int quotaPointsLimit) {
        this.spoonacularClient = spoonacularClient;
        this.quotaPointsCounter = quotaPointsCounter;
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

    private static RecipeIngredientsResponse prepareRecipeInformationResponse(SpoonacularGetRecipeInformationResponse recipeInformation) {
        var extendedIngredients = prepareExtendedIngredients(recipeInformation.getExtendedIngredients());
        var nutrients = prepareNutrients(recipeInformation.getNutrition().getNutrients());
        return RecipeIngredientsResponse.newBuilder()
            .withExtendedIngredients(extendedIngredients)
            .withNutrients(nutrients)
            .withReadyInMinutes(recipeInformation.getReadyInMinutes())
            .withServings(recipeInformation.getServings())
            .build();
    }

    private static List<Nutrient> prepareNutrients(List<SpoonacularGetRecipeInformationResponse.Nutrition.Nutrient> nutrients) {
        return nutrients.stream()
            .map(SpoonacularRecipeProvider::mapToNutrient)
            .collect(toList());
    }

    private static Nutrient mapToNutrient(SpoonacularGetRecipeInformationResponse.Nutrition.Nutrient nutrient) {
        return Nutrient.newBuilder()
            .withAmount(nutrient.getAmount())
            .withPercentOfDailyNeeds(nutrient.getPercentOfDailyNeeds())
            .withTitle(nutrient.getTitle())
            .withUnit(nutrient.getUnit())
            .build();
    }

    private static List<Ingredient> prepareExtendedIngredients(
        List<SpoonacularGetRecipeInformationResponse.ExtendedIngredient> extendedIngredients
    ) {
        return extendedIngredients.stream()
            .map(SpoonacularRecipeProvider::mapToIngredient)
            .collect(toList());
    }

    private static Ingredient mapToIngredient(
        SpoonacularGetRecipeInformationResponse.ExtendedIngredient extendedIngredient
    ) {
        return Ingredient.newBuilder()
            .withId(extendedIngredient.getId())
            .withAmount(extendedIngredient.getAmount())
            // TODO: 01.09.2020 Generowanie imageUrl
            .withImageUrl(extendedIngredient.getImage())
            .withName(extendedIngredient.getName())
            .withUnit(extendedIngredient.getUnit())
            .build();
    }

    private SearchRecipeResponse mapToSearchRecipeResponse(SpoonacularSearchRecipesResponse.SpoonacularSearchRecipeResponse recipe) {
        return new SearchRecipeResponse(recipe.getId(), recipe.getTitle(), recipe.getImage());
    }

}
