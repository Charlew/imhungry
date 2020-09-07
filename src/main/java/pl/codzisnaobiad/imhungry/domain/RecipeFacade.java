package pl.codzisnaobiad.imhungry.domain;

import pl.codzisnaobiad.imhungry.api.response.RecipeInstructionsResponse;
import pl.codzisnaobiad.imhungry.api.request.RecipeRequestModel;
import pl.codzisnaobiad.imhungry.api.response.RecipeIngredientsResponse;
import pl.codzisnaobiad.imhungry.api.response.SearchRecipesResponse;

public class RecipeFacade {

    private final RecipeProvider recipeProvider;

    public RecipeFacade(RecipeProvider recipeProvider) {
        this.recipeProvider = recipeProvider;
    }

    public SearchRecipesResponse searchRecipes(RecipeRequestModel recipeRequestModel) {
       return recipeProvider.searchRecipes(recipeRequestModel);
    }

    public RecipeIngredientsResponse getRecipeIngredients(String recipeId) {
        return recipeProvider.getRecipeIngredients(recipeId);
    }

    public RecipeInstructionsResponse getRecipeInstructions(String recipeId) {
        return recipeProvider.getRecipeInstructions(recipeId);
    }
}
