package pl.codzisnaobiad.imhungry.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RecipeConfiguration {

    @Bean
    RecipeFacade recipeFacade(RecipeProvider recipeProvider) {
        return new RecipeFacade(recipeProvider);
    }

}
