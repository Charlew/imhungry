package pl.codzisnaobiad.imhungry.infrastructure.spoonacular;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import pl.codzisnaobiad.imhungry.api.request.RecipeRequestModel;

import java.net.URI;

import static java.lang.Float.parseFloat;
import static java.lang.String.join;
import static java.util.Optional.ofNullable;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

class SpoonacularClient {
    private static final String RECIPES_SEGMENT = "recipes";
    private static final String INFORMATION_SEGMENT = "information";
    private static final String INCLUDE_NUTRITION_PARAM = "includeNutrition";
    private static final String API_KEY_PARAM = "apiKey";
    private static final String POINTS_USED_HEADER = "X-API_QUOTA-USED";

    private static final int MAX_RECIPES = 5;
    private static final boolean INSTRUCTIONS_REQUIRED = true;
    private static final boolean ADD_RECIPE_NUTRITION = true;
    private static final String EMPTY_STRING = "";
    private static final boolean INCLUDE_NUTRITION = true;

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
                .pathSegment("recipes", "complexSearch")
                .queryParam(API_KEY_PARAM, apiKey)
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

        var response = executeGetRequest(uri);
        return mapJsonToObject(response.getBody(), SpoonacularSearchRecipesResponse.class);
    }

    SpoonacularGetRecipeInformationResponse getRecipeInformationById(int id) {
        var uri = fromHttpUrl(baseUrl)
            .pathSegment(RECIPES_SEGMENT, String.valueOf(id), INFORMATION_SEGMENT)
            .queryParam(INCLUDE_NUTRITION_PARAM, INCLUDE_NUTRITION)
            .queryParam(API_KEY_PARAM, apiKey)
            .build()
            .toUri();

        var response = executeGetRequest(uri);
        return mapJsonToObject(response.getBody(), SpoonacularGetRecipeInformationResponse.class);
    }

    private HttpEntity<String> executeGetRequest(URI uri) {
        var response = http.getForEntity(uri, String.class);
        setQuotaPoints(response.getHeaders());
        return response;
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
