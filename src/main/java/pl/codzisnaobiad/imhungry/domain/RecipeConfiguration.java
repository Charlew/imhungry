package pl.codzisnaobiad.imhungry.domain;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RecipeConfiguration {

    @Bean
    RecipeFacade recipeFacade(@Qualifier("spoonacularRecipeProvider") RecipeProvider recipeProvider) {
        return new RecipeFacade(recipeProvider);
    }

}
