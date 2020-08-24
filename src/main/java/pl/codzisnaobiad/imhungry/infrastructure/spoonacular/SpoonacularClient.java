package pl.codzisnaobiad.imhungry.infrastructure.spoonacular;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import pl.codzisnaobiad.imhungry.api.request.RecipeRequestModel;

import java.util.List;

import static java.lang.Float.parseFloat;
import static java.lang.String.join;
import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

class SpoonacularClient {
    private static final String POINTS_USED_HEADER = "X-API_QUOTA-USED";
    private static final int MAX_RECIPES = 5;
    private static final boolean INSTRUCTIONS_REQUIRED = true;
    private static final boolean ADD_RECIPE_NUTRITION = true;
    private static final String EMPTY_STRING = "";

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

    List<SearchRecipesResponse> searchRecipes(RecipeRequestModel recipeRequestModel) {
        var uri = fromHttpUrl(baseUrl)
                .pathSegment("recipes", "complexSearch")
                .queryParam("apiKey", apiKey)
                .queryParam("includeIngredients", mapToCommaSeparatedQuery(recipeRequestModel.getIncludedIngredients()))
                .queryParam("excludeIngredients", mapToCommaSeparatedQuery(recipeRequestModel.getExcludedIngredients()))
                .queryParam("intolerances", mapToCommaSeparatedQuery(recipeRequestModel.getIntolerances()))
                .queryParam("query", ofNullable(recipeRequestModel.getNameQuery()).orElse(EMPTY_STRING))
                .queryParam("diet", ofNullable(recipeRequestModel.getDiet()).orElse(EMPTY_STRING))
                .queryParam("type", ofNullable(recipeRequestModel.getMealType()).orElse(EMPTY_STRING))
//                .queryParam("sort", ofNullable(recipeRequestModel.getSortBy()).orElse(EMPTY_STRING)) <- dziwnie działa, do zbadania
                .queryParam("number", MAX_RECIPES)
                .queryParam("instructionsRequired", INSTRUCTIONS_REQUIRED)
//                .queryParam("addRecipeNutrition", ADD_RECIPE_NUTRITION) <- bardzo dziwnie to działa, jak się doda to zwraca opis, wartości i kroki
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

    private String mapToCommaSeparatedQuery(Iterable<String> iterable) {
        return join(",", iterable);
    }

    private <T> T mapJsonToObject(String json, Class<T> targetClass) {
        try {
            return objectMapper.readValue(json, targetClass);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }
    }

}
