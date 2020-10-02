package pl.codzisnaobiad.imhungry.api.endpoint;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.codzisnaobiad.imhungry.domain.ingredient.IngredientFacade;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
class IngredientEndpoint {

    private final IngredientFacade ingredientFacade;

    IngredientEndpoint(IngredientFacade ingredientFacade) {
        this.ingredientFacade = ingredientFacade;
    }

    @GetMapping("/popular")
    List<String> getPopularIngredients() {
        return ingredientFacade.getPopularIngredients();
    }
}
