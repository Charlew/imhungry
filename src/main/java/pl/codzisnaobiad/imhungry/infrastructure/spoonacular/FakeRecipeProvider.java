package pl.codzisnaobiad.imhungry.infrastructure.spoonacular;

import org.springframework.stereotype.Component;
import pl.codzisnaobiad.imhungry.api.request.recipe.RecipeRequestModel;
import pl.codzisnaobiad.imhungry.api.response.recipe.Equipment;
import pl.codzisnaobiad.imhungry.api.response.recipe.Ingredient;
import pl.codzisnaobiad.imhungry.api.response.recipe.Length;
import pl.codzisnaobiad.imhungry.api.response.recipe.Nutrient;
import pl.codzisnaobiad.imhungry.api.response.recipe.RecipeIngredientsResponse;
import pl.codzisnaobiad.imhungry.api.response.recipe.RecipeInstructionResponse;
import pl.codzisnaobiad.imhungry.api.response.recipe.RecipeInstructionsResponse;
import pl.codzisnaobiad.imhungry.api.response.recipe.SearchRecipeResponse;
import pl.codzisnaobiad.imhungry.api.response.recipe.SearchRecipesResponse;
import pl.codzisnaobiad.imhungry.api.response.recipe.Step;
import pl.codzisnaobiad.imhungry.api.response.recipe.Temperature;
import pl.codzisnaobiad.imhungry.domain.recipe.RecipeProvider;

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

    @Override
    public RecipeInstructionsResponse getRecipeInstructions(String recipeId) {
        return new RecipeInstructionsResponse(fakeInstructions());
    }

    private List<RecipeInstructionResponse> fakeInstructions() {
        return List.of(
            new RecipeInstructionResponse("Grill bacon", List.of(Step.newBuilder()
                .withNumber(1)
                .withStep("Put them on the rack")
                .withEquipment(fakeEquipment())
                .withLength(new Length(20, "minutes"))
                .build()))
        );
    }

    private List<Equipment> fakeEquipment() {
        return List.of(
            new Equipment("grill", "grill.jpg", new Temperature(200f, "Celsius"))
        );
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
                .withImage("potato.jpg")
                .build());
    }
}
