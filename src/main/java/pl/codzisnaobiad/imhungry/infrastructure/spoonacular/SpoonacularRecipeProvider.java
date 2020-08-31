package pl.codzisnaobiad.imhungry.infrastructure.spoonacular;

import pl.codzisnaobiad.imhungry.api.request.RecipeRequestModel;
import pl.codzisnaobiad.imhungry.api.response.ExtendedIngredient;
import pl.codzisnaobiad.imhungry.api.response.FakeSearchRecipeResponse;
import pl.codzisnaobiad.imhungry.api.response.Nutrient;
import pl.codzisnaobiad.imhungry.api.response.SearchRecipeResponse;
import pl.codzisnaobiad.imhungry.api.response.SearchRecipesResponse;
import pl.codzisnaobiad.imhungry.api.response.RecipeInformationResponse;
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
    public RecipeInformationResponse getRecipeInformationById(int id) {
        var recipeInformation = spoonacularClient.getRecipeInformationById(id);
        return prepareRecipeInformationResponse(recipeInformation);
    }

    private static RecipeInformationResponse prepareRecipeInformationResponse(SpoonacularGetRecipeInformationResponse recipeInformation) {
        var extendedIngredients = prepareExtendedIngredients(recipeInformation.getExtendedIngredients());
        var nutrients = prepareNutrients(recipeInformation.getNutrition().getNutrients());
        return RecipeInformationResponse.newBuilder()
            .withExtendedIngredients(extendedIngredients)
            .withImageUrl(recipeInformation.getImageUrl())
            .withNutrients(nutrients)
            .withReadyInMinutes(recipeInformation.getReadyInMinutes())
            .withServings(recipeInformation.getServings())
            .withSourceUrl(recipeInformation.getSourceUrl())
            .withTitle(recipeInformation.getTitle())
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

    private static List<ExtendedIngredient> prepareExtendedIngredients(
        List<SpoonacularGetRecipeInformationResponse.ExtendedIngredient> extendedIngredients
    ) {
        return extendedIngredients.stream()
            .map(SpoonacularRecipeProvider::mapToExtendedIngredient)
            .collect(toList());
    }

    private static ExtendedIngredient mapToExtendedIngredient(
        SpoonacularGetRecipeInformationResponse.ExtendedIngredient extendedIngredient
    ) {
        return ExtendedIngredient.newBuilder()
            .withId(extendedIngredient.getId())
            .withAmount(extendedIngredient.getAmount())
            .withName(extendedIngredient.getName())
            .withOriginal(extendedIngredient.getOriginal())
            .withUnit(extendedIngredient.getUnit())
            .build();
    }

    private SearchRecipeResponse mapToSearchRecipeResponse(SpoonacularSearchRecipesResponse.SpoonacularSearchRecipeResponse recipe) {
        return new SearchRecipeResponse(recipe.getId(), recipe.getTitle(), recipe.getImage());
    }

}
