package pl.codzisnaobiad.imhungry.infrastructure.spoonacular;

import pl.codzisnaobiad.imhungry.api.request.recipe.RecipeRequestModel;
import pl.codzisnaobiad.imhungry.api.response.recipe.RecipeIngredientsResponse;
import pl.codzisnaobiad.imhungry.api.response.recipe.RecipeInstructionsResponse;
import pl.codzisnaobiad.imhungry.api.response.recipe.SearchRecipesResponse;
import pl.codzisnaobiad.imhungry.domain.recipe.RecipeProvider;

class SpoonacularRecipeProvider implements RecipeProvider {
    private final RecipeProvider fakeRecipeProvider;
    private final SpoonacularClient spoonacularClient;
    private final QuotaPointsCounter quotaPointsCounter;
    private final SpoonacularMapper spoonacularMapper;

    SpoonacularRecipeProvider(RecipeProvider fakeRecipeProvider,
                              SpoonacularClient spoonacularClient,
                              QuotaPointsCounter quotaPointsCounter,
                              SpoonacularMapper spoonacularMapper
    ) {
        this.fakeRecipeProvider = fakeRecipeProvider;
        this.spoonacularClient = spoonacularClient;
        this.quotaPointsCounter = quotaPointsCounter;
        this.spoonacularMapper = spoonacularMapper;
    }

    @Override
    public SearchRecipesResponse searchRecipes(RecipeRequestModel recipeRequestModel) {
        if (quotaPointsCounter.quotaPointsExceeded()) {
            return fakeRecipeProvider.searchRecipes(recipeRequestModel);
        }
        var recipes = spoonacularClient.searchRecipes(recipeRequestModel);
        return spoonacularMapper.prepareSearchRecipesResponse(recipes);
    }

    @Override
    public RecipeIngredientsResponse getRecipeIngredients(String id) {
        if (quotaPointsCounter.quotaPointsExceeded()) {
            return fakeRecipeProvider.getRecipeIngredients(id);
        }
        var recipeInformation = spoonacularClient.getRecipeInformationById(id);
        return spoonacularMapper.prepareRecipeIngredientsResponse(recipeInformation);
    }

    @Override
    public RecipeInstructionsResponse getRecipeInstructions(String recipeId) {
        if (quotaPointsCounter.quotaPointsExceeded()) {
            return fakeRecipeProvider.getRecipeInstructions(recipeId);
        }
        var recipeInstructions = spoonacularClient.getRecipeInstructions(recipeId);
        return spoonacularMapper.prepareRecipeInstructionsResponse(recipeInstructions);
    }

}
