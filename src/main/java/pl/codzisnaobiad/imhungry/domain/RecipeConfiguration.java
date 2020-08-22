package pl.codzisnaobiad.imhungry.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.codzisnaobiad.imhungry.api.RecipeResponse;

@Configuration
class RecipeConfiguration {

    @Bean
    RecipeFacade recipeFacade(RecipeProvider recipeProvider, Randomizer<RecipeResponse> randomizer) {
        return new RecipeFacade(recipeProvider, randomizer);
    }

    @Bean
    Randomizer<RecipeResponse> randomizer() {
        return new DefaultRandomizer<>();
    }

}
