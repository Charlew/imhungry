package pl.codzisnaobiad.imhungry.infrastructure.spoonacular;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import pl.codzisnaobiad.imhungry.api.exception.SpoonacularCommunicationException;
import pl.codzisnaobiad.imhungry.api.request.RecipeRequestModel;

import java.net.URI;

import static java.lang.Float.parseFloat;
import static java.lang.String.join;
import static java.util.Optional.ofNullable;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

class SpoonacularClient {
    private static final String RECIPES_SEGMENT = "recipes";
    private static final String INFORMATION_SEGMENT = "information";
    private static final String COMPLEX_SEARCH_SEGMENT = "complexSearch";
    private static final String ANALYZED_INSTRUCTIONS_SEGMENT = "analyzedInstructions";
    private static final String INCLUDE_NUTRITION_PARAM = "includeNutrition";
    private static final String API_KEY_PARAM = "apiKey";
    private static final String INCLUDE_INGREDIENTS_PARAM = "includeIngredients";
    private static final String EXCLUDE_INGREDIENTS_PARAM = "excludeIngredients";
    private static final String INTOLERANCES_PARAM = "intolerances";
    private static final String QUERY_PARAM = "query";
    private static final String DIET_PARAM = "diet";
    private static final String TYPE_PARAM = "type";
    private static final String NUMBER_PARAM = "number";
    private static final String STEP_BREAKDOWN_PARAM = "stepBreakdown";
    private static final String INSTRUCTIONS_REQUIRED_PARAM = "instructionsRequired";
    private static final String POINTS_USED_HEADER = "X-API-QUOTA-USED";

    private static final int MAX_RECIPES = 5;
    private static final boolean INSTRUCTIONS_REQUIRED = true;
    private static final String EMPTY_STRING = "";
    private static final boolean INCLUDE_NUTRITION = true;
    private static final boolean STEP_BREAKDOWN = true;

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

    SpoonacularSearchRecipesResponse searchRecipes(RecipeRequestModel recipeRequestModel) {
        var uri = fromHttpUrl(baseUrl)
                .pathSegment(RECIPES_SEGMENT, COMPLEX_SEARCH_SEGMENT)
                .queryParam(API_KEY_PARAM, apiKey)
                .queryParam(INCLUDE_INGREDIENTS_PARAM, mapToCommaSeparatedQuery(recipeRequestModel.getIncludedIngredients()))
                .queryParam(EXCLUDE_INGREDIENTS_PARAM, mapToCommaSeparatedQuery(recipeRequestModel.getExcludedIngredients()))
                .queryParam(INTOLERANCES_PARAM, mapToCommaSeparatedQuery(recipeRequestModel.getIntolerances()))
                .queryParam(QUERY_PARAM, ofNullable(recipeRequestModel.getNameQuery()).orElse(EMPTY_STRING))
                .queryParam(DIET_PARAM, ofNullable(recipeRequestModel.getDiet()).orElse(EMPTY_STRING))
                .queryParam(TYPE_PARAM, ofNullable(recipeRequestModel.getMealType()).orElse(EMPTY_STRING))
                .queryParam(NUMBER_PARAM, MAX_RECIPES)
                .queryParam(INSTRUCTIONS_REQUIRED_PARAM, INSTRUCTIONS_REQUIRED)
                .encode()
                .build()
                .toUri();

        var response = executeGetRequest(uri);
        return mapJsonToObject(response.getBody(), SpoonacularSearchRecipesResponse.class);
    }

    SpoonacularRecipeInformationResponse getRecipeInformationById(String recipeId) {
        var uri = fromHttpUrl(baseUrl)
            .pathSegment(RECIPES_SEGMENT, recipeId, INFORMATION_SEGMENT)
            .queryParam(INCLUDE_NUTRITION_PARAM, INCLUDE_NUTRITION)
            .queryParam(API_KEY_PARAM, apiKey)
            .build()
            .toUri();

        var response = executeGetRequest(uri);
        return mapJsonToObject(response.getBody(), SpoonacularRecipeInformationResponse.class);
    }

    SpoonacularAnalyzedInstructionsResponse[] getRecipeInstructions(String recipeId) {
        var uri = fromHttpUrl(baseUrl)
            .pathSegment(RECIPES_SEGMENT, recipeId, ANALYZED_INSTRUCTIONS_SEGMENT)
            .queryParam(STEP_BREAKDOWN_PARAM, STEP_BREAKDOWN)
            .queryParam(API_KEY_PARAM, apiKey)
            .build()
            .toUri();

        var response = executeGetRequest(uri);
        return mapJsonToObject(response.getBody(), SpoonacularAnalyzedInstructionsResponse[].class);
    }

    private HttpEntity<String> executeGetRequest(URI uri) {
        try {
            var response = http.getForEntity(uri, String.class);
            setQuotaPoints(response.getHeaders());
            return response;
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new SpoonacularCommunicationException(e.getMessage());
        }
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
