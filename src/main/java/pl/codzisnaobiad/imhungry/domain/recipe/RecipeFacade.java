package pl.codzisnaobiad.imhungry.domain.recipe;

import pl.codzisnaobiad.imhungry.api.request.recipe.RecipeRequestModel;
import pl.codzisnaobiad.imhungry.api.response.recipe.RecipeIngredientsResponse;
import pl.codzisnaobiad.imhungry.api.response.recipe.RecipeInstructionsResponse;
import pl.codzisnaobiad.imhungry.api.response.recipe.SearchRecipesResponse;
import pl.codzisnaobiad.imhungry.domain.ingredient.IngredientFacade;

import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

public class RecipeFacade {

    private final RecipeProvider recipeProvider;
    private final IngredientFacade ingredientFacade;

    public RecipeFacade(RecipeProvider recipeProvider, IngredientFacade ingredientFacade) {
        this.recipeProvider = recipeProvider;
        this.ingredientFacade = ingredientFacade;
    }

    public SearchRecipesResponse searchRecipes(RecipeRequestModel recipeRequestModel) {
        var searchRecipesResponse = recipeProvider.searchRecipes(recipeRequestModel);
        if (!isEmpty(recipeRequestModel.getIncludedIngredients())) {
            saveIngredients(recipeRequestModel.getIncludedIngredients());
        }
        return searchRecipesResponse;
    }

    public RecipeIngredientsResponse getRecipeIngredients(String recipeId) {
        return recipeProvider.getRecipeIngredients(recipeId);
    }

    public RecipeInstructionsResponse getRecipeInstructions(String recipeId) {
        return recipeProvider.getRecipeInstructions(recipeId);
    }

    private void saveIngredients(List<String> includedIngredients) {
        includedIngredients.forEach(ingredientFacade::findByNameAndReplace);
    }
}
