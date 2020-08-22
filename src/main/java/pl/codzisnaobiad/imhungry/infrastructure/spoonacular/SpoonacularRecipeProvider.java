package pl.codzisnaobiad.imhungry.infrastructure.spoonacular;

import pl.codzisnaobiad.imhungry.api.Ingredient;
import pl.codzisnaobiad.imhungry.api.RecipeResponse;
import pl.codzisnaobiad.imhungry.domain.RecipeProvider;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;

class SpoonacularRecipeProvider implements RecipeProvider {

    private final SpoonacularClient spoonacularClient;

    SpoonacularRecipeProvider(SpoonacularClient spoonacularClient) {
        this.spoonacularClient = spoonacularClient;
    }

    @Override
    public List<RecipeResponse> getRecipes(List<String> ingredients, int maxRecipes) {
        return spoonacularClient.searchRecipes(ingredients, maxRecipes)
                .stream()
                .map(this::mapToRecipeResponse)
                .collect(toList());
    }

    private RecipeResponse mapToRecipeResponse(SearchRecipesResponse recipe) {
        var mergedIngredients = concat(recipe.getUsedIngredients().stream(), recipe.getUnusedIngredients().stream())
                .collect(toList());

        return new RecipeResponse(recipe.getTitle(), recipe.getImage(), mergedIngredients.stream().map(this::mapToIngredient).collect(toList()));
    }

    private Ingredient mapToIngredient(SearchRecipesResponse.IngredientResponse ingredientResponse) {
        return new Ingredient(ingredientResponse.getName(), ingredientResponse.getImage(),
                ingredientResponse.getAmount(), ingredientResponse.getUnit());
    }

}
