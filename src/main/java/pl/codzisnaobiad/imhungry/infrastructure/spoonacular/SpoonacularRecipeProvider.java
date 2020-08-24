package pl.codzisnaobiad.imhungry.infrastructure.spoonacular;

import pl.codzisnaobiad.imhungry.api.FakeRecipeResponse;
import pl.codzisnaobiad.imhungry.api.Ingredient;
import pl.codzisnaobiad.imhungry.api.RecipeResponse;
import pl.codzisnaobiad.imhungry.domain.RecipeProvider;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;

class SpoonacularRecipeProvider implements RecipeProvider {
    private static final int LIMIT_OF_QUOTA_POINTS = 149;

    private final SpoonacularClient spoonacularClient;
    private final QuotaPointsCounter quotaPointsCounter;

    SpoonacularRecipeProvider(SpoonacularClient spoonacularClient, QuotaPointsCounter quotaPointsCounter) {
        this.spoonacularClient = spoonacularClient;
        this.quotaPointsCounter = quotaPointsCounter;
    }

    @Override
    public List<RecipeResponse> getRecipes(List<String> ingredients, int maxRecipes) {
        if (quotaPointsCounter.getPointsCount() >= LIMIT_OF_QUOTA_POINTS) {
            return List.of(new FakeRecipeResponse());
        }
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
