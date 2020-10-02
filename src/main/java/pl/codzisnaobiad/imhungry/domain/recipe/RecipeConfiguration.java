package pl.codzisnaobiad.imhungry.domain.recipe;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.codzisnaobiad.imhungry.domain.ingredient.IngredientFacade;

@Configuration
class RecipeConfiguration {

    @Bean
    RecipeFacade recipeFacade(@Qualifier("spoonacularRecipeProvider") RecipeProvider recipeProvider,
                              IngredientFacade ingredientFacade) {
        return new RecipeFacade(recipeProvider, ingredientFacade);
    }

}
