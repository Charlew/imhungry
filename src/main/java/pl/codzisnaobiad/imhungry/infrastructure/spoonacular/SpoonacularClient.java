package pl.codzisnaobiad.imhungry.infrastructure.spoonacular;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.lang.Float.parseFloat;
import static java.lang.String.join;
import static java.util.Arrays.asList;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

class SpoonacularClient {
    private static final String POINTS_USED_HEADER = "X-API_QUOTA-USED";

    private final RestTemplate http;
    private final ObjectMapper objectMapper;
    private final QuotaPointsCounter quotaPointsCounter;
    private final String baseUrl;
    private final String apiKey;

    SpoonacularClient(RestTemplate http, ObjectMapper objectMapper, String baseUrl, String apiKey,
                      QuotaPointsCounter quotaPointsCounter) {
        this.http = http;
        this.objectMapper = objectMapper;
        this.quotaPointsCounter = quotaPointsCounter;
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
    }

    List<SearchRecipesResponse> searchRecipes(List<String> ingredients, int maxRecipes) {
        var uri = fromHttpUrl(baseUrl)
                .pathSegment("recipes", "findByIngredients")
                .queryParam("apiKey", apiKey)
                .queryParam("ingredients", mapToIngredientsQuery(ingredients))
                .queryParam("number", maxRecipes)
                .queryParam("ranking", SearchRecipeRanking.MAXIMIZE_USED_INGREDIENTS.getValue())
                .encode()
                .build()
                .toUri();
        HttpEntity<String> response = http.getForEntity(uri, String.class);
        setQuotaPoints(response.getHeaders());
        return asList(mapJsonToObject(response.getBody(), SearchRecipesResponse[].class));
    }

    private void setQuotaPoints(HttpHeaders headers) {
        var pointsCount = headers.get(POINTS_USED_HEADER);
        if (pointsCount != null) {
            quotaPointsCounter.setQuotaPoints((int) parseFloat(pointsCount.get(0)));
        }
    }

    private String mapToIngredientsQuery(List<String> ingredients) {
        return join(",", ingredients);
    }

    private <T> T mapJsonToObject(String json, Class<T> targetClass) {
        try {
            return objectMapper.readValue(json, targetClass);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }
    }

    private enum SearchRecipeRanking {

        MAXIMIZE_USED_INGREDIENTS(1),
        MINIMIZE_MISSING_INGREDIENTS(2);

        private final int value;

        SearchRecipeRanking(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }

}
