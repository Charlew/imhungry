package pl.codzisnaobiad.imhungry.api.endpoint;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.codzisnaobiad.imhungry.api.request.recipe.RecipeRequestModel;
import pl.codzisnaobiad.imhungry.api.response.recipe.RecipeIngredientsResponse;
import pl.codzisnaobiad.imhungry.api.response.recipe.RecipeInstructionsResponse;
import pl.codzisnaobiad.imhungry.api.response.recipe.SearchRecipesResponse;
import pl.codzisnaobiad.imhungry.domain.recipe.RecipeFacade;

import java.util.List;

@RestController
@RequestMapping("/recipes")
class RecipeEndpoint {

    private final RecipeFacade recipeFacade;

    RecipeEndpoint(RecipeFacade recipeFacade) {
        this.recipeFacade = recipeFacade;
    }

    @GetMapping("/search")
    SearchRecipesResponse searchRecipes(
            @RequestParam(value = "includedIngredient") List<String> includedIngredients,
            @RequestParam(value = "excludedIngredient", required = false) List<String> excludedIngredients,
            @RequestParam(value = "intolerance", required = false) List<String> intolerances,
            @RequestParam(required = false) String nameQuery,
            @RequestParam(required = false) String diet,
            @RequestParam(required = false) String mealType
    ) {
        var recipeRequestModel = RecipeRequestModel.builder()
            .withIncludedIngredients(includedIngredients)
            .withExcludedIngredients(excludedIngredients)
            .withIntolerances(intolerances)
            .withNameQuery(nameQuery)
            .withDiet(diet)
            .withMealType(mealType)
            .build();

        return recipeFacade.searchRecipes(recipeRequestModel);
    }

    @GetMapping("/{id}/ingredients")
    RecipeIngredientsResponse getRecipeIngredients(@PathVariable("id") String recipeId) {
        return recipeFacade.getRecipeIngredients(recipeId);
    }

    @GetMapping("/{id}/instructions")
    RecipeInstructionsResponse getRecipeInstructions(@PathVariable("id") String recipeId) {
        return recipeFacade.getRecipeInstructions(recipeId);
    }
}
