package pl.codzisnaobiad.imhungry.api.endpoint;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.codzisnaobiad.imhungry.api.request.RecipeRequestModelMapper;
import pl.codzisnaobiad.imhungry.api.response.RecipeIngredientsResponse;
import pl.codzisnaobiad.imhungry.api.response.RecipeInstructionsResponse;
import pl.codzisnaobiad.imhungry.api.response.SearchRecipesResponse;
import pl.codzisnaobiad.imhungry.domain.RecipeFacade;

@RestController
@RequestMapping("/recipes")
class RecipeEndpoint {

    private final RecipeFacade recipeFacade;
    private final RecipeRequestModelMapper recipeRequestModelMapper;

    RecipeEndpoint(RecipeFacade recipeFacade, RecipeRequestModelMapper recipeRequestModelMapper) {
        this.recipeFacade = recipeFacade;
        this.recipeRequestModelMapper = recipeRequestModelMapper;
    }

    // TODO: 25/08/2020 dodac swaggera do paramow
    @GetMapping("/search")
    SearchRecipesResponse searchRecipes(
            @RequestParam MultiValueMap<String, String> queryParameters
    ) {
        return recipeFacade.searchRecipes(recipeRequestModelMapper.toRequestModel(queryParameters));
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
