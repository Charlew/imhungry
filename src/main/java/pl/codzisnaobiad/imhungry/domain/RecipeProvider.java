package pl.codzisnaobiad.imhungry.domain;

import pl.codzisnaobiad.imhungry.api.request.RecipeRequestModel;
import pl.codzisnaobiad.imhungry.api.response.RecipeInformationResponse;
import pl.codzisnaobiad.imhungry.api.response.SearchRecipesResponse;

public interface RecipeProvider {

    SearchRecipesResponse searchRecipes(RecipeRequestModel recipeRequestModel);

    RecipeInformationResponse getRecipeInformationById(int id);
}
