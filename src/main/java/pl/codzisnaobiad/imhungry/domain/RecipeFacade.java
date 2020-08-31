package pl.codzisnaobiad.imhungry.domain;

import pl.codzisnaobiad.imhungry.api.request.RecipeRequestModel;
import pl.codzisnaobiad.imhungry.api.response.RecipeInformationResponse;
import pl.codzisnaobiad.imhungry.api.response.SearchRecipesResponse;

public class RecipeFacade {

    private final RecipeProvider recipeProvider;

    public RecipeFacade(RecipeProvider recipeProvider) {
        this.recipeProvider = recipeProvider;
    }

    public SearchRecipesResponse searchRecipes(RecipeRequestModel recipeRequestModel) {
       return recipeProvider.searchRecipes(recipeRequestModel);
    }

    public RecipeInformationResponse getRecipeInformationById(int id) {
        return recipeProvider.getRecipeInformationById(id);
    }
}
