package pl.codzisnaobiad.imhungry.utils

import org.springframework.util.MultiValueMap
import org.springframework.web.util.UriComponentsBuilder

import static java.lang.String.join
import static java.util.Optional.ofNullable

class ParamsMapper {

    static String EMPTY_STRING = ""

    static String queryParamsToSpoonacularUrl(MultiValueMap<String, String> queryParams) {
        return UriComponentsBuilder.newInstance()
                .queryParam("apiKey", "API_KEY")
                .queryParam("includeIngredients", joinWithCommaDelimiter(queryParams.get("includedIngredient")))
                .queryParam("excludeIngredients", joinWithCommaDelimiter(optional(queryParams.get("excludedIngredient"))))
                .queryParam("intolerances", joinWithCommaDelimiter(optional(queryParams.get("intolerance"))))
                .queryParam("query", optional(queryParams.getFirst("nameQuery")))
                .queryParam("diet", optional(queryParams.getFirst("diet")))
                .queryParam("type", optional(queryParams.getFirst("mealType")))
                .queryParam("number", 5)
                .queryParam("instructionsRequired", true)
                .build()
                .toString()

    }

    static String queryParamsToImHungryUrl(String url, MultiValueMap<String, String> queryParams) {
        return UriComponentsBuilder.newInstance()
                .fromHttpUrl(url)
                .queryParam("includedIngredient", queryParams.get("includedIngredient"))
                .queryParam("excludedIngredient", optional(queryParams.get("excludedIngredient")))
                .queryParam("intolerance", optional(queryParams.get("intolerance")))
                .queryParam("nameQuery", optional(queryParams.getFirst("nameQuery")))
                .queryParam("diet", optional(queryParams.getFirst("diet")))
                .queryParam("mealType", optional(queryParams.getFirst("mealType")))
                .build()
                .toString()
    }

    static String recipeInformationQueryParamsToSpoonacularUrl() {
        UriComponentsBuilder.newInstance()
            .queryParam("includeNutrition", true)
            .queryParam("apiKey", "API_KEY")
            .build()
            .toString()
    }

    static List<String> optional(List<String> params) {
        return ofNullable(params).orElse(Collections.emptyList())
    }

    static String optional(String param) {
        return ofNullable(param).orElse(EMPTY_STRING)
    }

    static String joinWithCommaDelimiter(List<String> list) {
        return join(",", list)
    }

}
