package pl.codzisnaobiad.imhungry.domain;

import pl.codzisnaobiad.imhungry.api.response.RecipeInstructionsResponse;
import pl.codzisnaobiad.imhungry.api.request.RecipeRequestModel;
import pl.codzisnaobiad.imhungry.api.response.RecipeIngredientsResponse;
import pl.codzisnaobiad.imhungry.api.response.SearchRecipesResponse;

public interface RecipeProvider {

    SearchRecipesResponse searchRecipes(RecipeRequestModel recipeRequestModel);

    RecipeIngredientsResponse getRecipeIngredients(String recipeId);

    RecipeInstructionsResponse getRecipeInstructions(String recipeId);
}
