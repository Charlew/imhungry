package pl.codzisnaobiad.imhungry.domain;

import pl.codzisnaobiad.imhungry.api.RecipeResponse;

import java.util.List;

public class RecipeFacade {

    private static final int MAX_RECIPES = 10;

    private final RecipeProvider recipeProvider;
    private final Randomizer<RecipeResponse> randomizer;

    public RecipeFacade(RecipeProvider recipeProvider, Randomizer<RecipeResponse> randomizer) {
        this.recipeProvider = recipeProvider;
        this.randomizer = randomizer;
    }

    public RecipeResponse getRandomRecipe(List<String> ingredients) {
        var recipes = recipeProvider.getRecipes(ingredients, MAX_RECIPES);

        return randomizer.randomize(recipes);
    }

}
