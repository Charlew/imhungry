package pl.codzisnaobiad.imhungry.infrastructure.spoonacular;

import pl.codzisnaobiad.imhungry.api.request.RecipeRequestModel;
import pl.codzisnaobiad.imhungry.api.response.FakeSearchRecipeResponse;
import pl.codzisnaobiad.imhungry.api.response.SearchRecipeResponse;
import pl.codzisnaobiad.imhungry.api.response.SearchRecipesResponse;
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
    public SearchRecipesResponse searchRecipes(RecipeRequestModel recipeRequestModel) {
        if (quotaPointsCounter.getPointsCount() >= quotaPointsLimit) {
            return new SearchRecipesResponse(List.of(new FakeSearchRecipeResponse()));
        }

        return new SearchRecipesResponse(spoonacularClient.searchRecipes(recipeRequestModel).getResults()
                .stream()
                .map(this::mapToSearchRecipeResponse)
                .collect(toList()));
    }

    private SearchRecipeResponse mapToSearchRecipeResponse(SpoonacularSearchRecipesResponse.SpoonacularSearchRecipeResponse recipe) {
        return new SearchRecipeResponse(recipe.getId(), recipe.getTitle(), recipe.getImage());
    }

}
