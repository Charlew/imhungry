package pl.codzisnaobiad.imhungry.api;

import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.codzisnaobiad.imhungry.domain.RecipeFacade;

import java.util.List;

@RestController
@RequestMapping("/recipes")
class RecipeEndpoint {

    private final RecipeFacade recipeFacade;

    RecipeEndpoint(RecipeFacade recipeFacade) {
        this.recipeFacade = recipeFacade;
    }

    @GetMapping("/random")
    RecipeResponse getRandomRecipe(
            @ApiParam(name = "ingredient", allowMultiple = true, example = "apple")
            @RequestParam(value = "ingredient") List<String> ingredients
    ) {
        return recipeFacade.getRandomRecipe(ingredients);
    }

}
