package pl.codzisnaobiad.imhungry.domain.recipe;

import pl.codzisnaobiad.imhungry.api.response.recipe.RecipeInstructionsResponse;
import pl.codzisnaobiad.imhungry.api.request.recipe.RecipeRequestModel;
import pl.codzisnaobiad.imhungry.api.response.recipe.RecipeIngredientsResponse;
import pl.codzisnaobiad.imhungry.api.response.recipe.SearchRecipesResponse;

public interface RecipeProvider {

    SearchRecipesResponse searchRecipes(RecipeRequestModel recipeRequestModel);

    RecipeIngredientsResponse getRecipeIngredients(String recipeId);

    RecipeInstructionsResponse getRecipeInstructions(String recipeId);
}
