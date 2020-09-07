package pl.codzisnaobiad.imhungry.infrastructure.spoonacular;

import org.springframework.stereotype.Component;
import pl.codzisnaobiad.imhungry.api.request.RecipeRequestModel;
import pl.codzisnaobiad.imhungry.api.response.Equipment;
import pl.codzisnaobiad.imhungry.api.response.Ingredient;
import pl.codzisnaobiad.imhungry.api.response.Length;
import pl.codzisnaobiad.imhungry.api.response.Nutrient;
import pl.codzisnaobiad.imhungry.api.response.RecipeIngredientsResponse;
import pl.codzisnaobiad.imhungry.api.response.RecipeInstructionResponse;
import pl.codzisnaobiad.imhungry.api.response.RecipeInstructionsResponse;
import pl.codzisnaobiad.imhungry.api.response.SearchRecipeResponse;
import pl.codzisnaobiad.imhungry.api.response.SearchRecipesResponse;
import pl.codzisnaobiad.imhungry.api.response.Step;
import pl.codzisnaobiad.imhungry.api.response.Temperature;
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

    @Override
    public RecipeInstructionsResponse getRecipeInstructions(String recipeId) {
        return new RecipeInstructionsResponse(fakeInstructions());
    }

    private List<RecipeInstructionResponse> fakeInstructions() {
        return List.of(
            new RecipeInstructionResponse("Grill bacon", List.of(Step.newBuilder()
                .withNumber(1)
                .withStep("Put them on the rack")
                .withIngredients(fakeIngredientsWithOnlyNameAndImage())
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

    private List<Ingredient> fakeIngredientsWithOnlyNameAndImage() {
        return List.of(
            Ingredient.newBuilder()
                .withName("bacon")
                .withImage("bacon.jpg")
                .build()
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
