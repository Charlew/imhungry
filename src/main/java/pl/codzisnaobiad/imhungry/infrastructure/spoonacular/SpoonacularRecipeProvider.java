package pl.codzisnaobiad.imhungry.infrastructure.spoonacular;

import pl.codzisnaobiad.imhungry.api.request.RecipeRequestModel;
import pl.codzisnaobiad.imhungry.api.response.FakeRecipeResponse;
import pl.codzisnaobiad.imhungry.api.response.RecipeResponse;
import pl.codzisnaobiad.imhungry.api.response.RecipesResponse;
import pl.codzisnaobiad.imhungry.domain.RecipeProvider;

import java.util.List;

import static java.util.stream.Collectors.toList;

class SpoonacularRecipeProvider implements RecipeProvider {
    private final SpoonacularClient spoonacularClient;
    private final QuotaPointsCounter quotaPointsCounter;
    private final int quotaPointsLimit;

    SpoonacularRecipeProvider(SpoonacularClient spoonacularClient,
                              QuotaPointsCounter quotaPointsCounter,
                              int quotaPointsLimit) {
        this.spoonacularClient = spoonacularClient;
        this.quotaPointsCounter = quotaPointsCounter;
        this.quotaPointsLimit = quotaPointsLimit;
    }

    @Override
    public RecipesResponse searchRecipes(RecipeRequestModel recipeRequestModel) {
        if (quotaPointsCounter.getPointsCount() >= quotaPointsLimit) {
            return new RecipesResponse(List.of(new FakeRecipeResponse()));
        }

        return new RecipesResponse(spoonacularClient.searchRecipes(recipeRequestModel)
                .stream()
                .map(this::mapToRecipeResponse)
                .collect(toList()));
    }

    private RecipeResponse mapToRecipeResponse(SearchRecipesResponse recipe) {
        return new RecipeResponse(recipe.getId(), recipe.getTitle(), recipe.getImage());
    }

}
