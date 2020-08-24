package pl.codzisnaobiad.imhungry.domain;

import pl.codzisnaobiad.imhungry.api.request.RecipeRequestModel;
import pl.codzisnaobiad.imhungry.api.response.RecipesResponse;

public class RecipeFacade {

    private final RecipeProvider recipeProvider;

    public RecipeFacade(RecipeProvider recipeProvider) {
        this.recipeProvider = recipeProvider;
    }

    public RecipesResponse searchRecipes(RecipeRequestModel recipeRequestModel) {
       return recipeProvider.searchRecipes(recipeRequestModel);
    }

}
