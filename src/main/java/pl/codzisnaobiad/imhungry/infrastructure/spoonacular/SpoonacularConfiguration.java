package pl.codzisnaobiad.imhungry.infrastructure.spoonacular;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pl.codzisnaobiad.imhungry.domain.RecipeProvider;

import java.time.Duration;

@Configuration
class SpoonacularConfiguration {

    @Bean
    RecipeProvider spoonacularRecipeProvider(SpoonacularClient spoonacularClient,
                                             QuotaPointsCounter quotaPointsCounter,
                                             @Value("${client.spoonacular.quota-points-limit:149}") int quotaPointsLimit) {
        return new SpoonacularRecipeProvider(spoonacularClient, quotaPointsCounter, quotaPointsLimit);
    }

    @Bean
    SpoonacularClient spoonacularClient(RestTemplate spoonacularRestTemplate,
                                        ObjectMapper objectMapper,
                                        QuotaPointsCounter quotaPointsCounter,
                                        @Value("${client.spoonacular.base-url}") String baseUrl,
                                        @Value("${client.spoonacular.api-key}") String apiKey
    ) {
        return new SpoonacularClient(spoonacularRestTemplate, objectMapper, baseUrl, apiKey, quotaPointsCounter);
    }

    @Bean
    RestTemplate spoonacularRestTemplate(@Value("${client.spoonacular.connection-timeout}") Duration connectionTimeout,
                                         @Value("${client.spoonacular.read-timeout}") Duration readTimeout,
                                         RestTemplateBuilder restTemplateBuilder
    ) {
        return restTemplateBuilder
                .setConnectTimeout(connectionTimeout)
                .setReadTimeout(readTimeout)
                .build();
    }

    @Bean
    ObjectMapper spoonacularObjectMapper() {
        return new ObjectMapper();
    }

}
