package pl.codzisnaobiad.imhungry.api;

import java.util.List;

public class FakeRecipeResponse extends RecipeResponse {
    public FakeRecipeResponse() {
        super("Polish kebab", "test", sampleIngredients());
    }

    private static List<Ingredient> sampleIngredients() {
        return List.of(
            new Ingredient("Mutton", "test", 150f, "g"),
            new Ingredient("Salad", "test", 50f, "g"),
            new Ingredient("Garlic sauce", "test", 50f, "g")
        );
    }
}
