package pl.codzisnaobiad.imhungry.infrastructure.spoonacular;

import org.springframework.stereotype.Component;
import pl.codzisnaobiad.imhungry.api.request.*;
import pl.codzisnaobiad.imhungry.api.response.*;
import pl.codzisnaobiad.imhungry.domain.RecipeProvider;

import java.util.List;

@Component
public class FakeRecipeProvider implements RecipeProvider {

    @Override
    public SearchRecipesResponse searchRecipes(RecipeRequestModel recipeRequestModel) {
        return new SearchRecipesResponse(List.of(new SearchRecipeResponse("123", "Polish kebab", "kebab.jpg")));
    }

    @Override
    public RecipeIngredientsResponse getRecipeIngredients(String recipeId) {
        return RecipeIngredientsResponse.newBuilder()
            .withIngredients(fakeIngredients())
            .withNutrients(fakeNutrients())
            .withReadyInMinutes(10)
            .withServings(1)
            .build();
    }

    private List<Nutrient> fakeNutrients() {
        return List.of(
            Nutrient.newBuilder()
                .withName("Calories")
                .withAmount(100f)
                .build()
        );
    }

    private List<Ingredient> fakeIngredients() {
        return List.of(
            Ingredient.newBuilder()
                .withId("456")
                .withName("potato")
                .withUnit("g")
                .withAmount(100f)
                .withImageUrl("potato.jpg")
                .build());
    }
}
