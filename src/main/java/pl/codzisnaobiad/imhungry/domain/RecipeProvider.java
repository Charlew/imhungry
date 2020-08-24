package pl.codzisnaobiad.imhungry.domain;

import pl.codzisnaobiad.imhungry.api.request.RecipeRequestModel;
import pl.codzisnaobiad.imhungry.api.response.RecipesResponse;

public interface RecipeProvider {

    RecipesResponse searchRecipes(RecipeRequestModel recipeRequestModel);

}
