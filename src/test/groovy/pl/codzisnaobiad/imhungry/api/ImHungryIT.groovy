package pl.codzisnaobiad.imhungry.api

import org.springframework.http.ResponseEntity
import org.springframework.util.LinkedMultiValueMap
import pl.codzisnaobiad.imhungry.IntegrationSpec
import pl.codzisnaobiad.imhungry.api.response.recipe.SearchRecipesResponse

import static pl.codzisnaobiad.imhungry.utils.ParamsMapper.queryParamsWithOnlyIncludedIngredientsToImHungry

class ImHungryIT extends IntegrationSpec {
    private static final String SEARCH_RECIPES_PATH = "/recipes/search"

    def "should search recipes by ingredients and return the most popular ingredients"() {
        given:
            def firstRequestQueryParameters = new LinkedMultiValueMap()
            firstRequestQueryParameters.addAll("includedIngredient", ["banana", "chocolate"])

            def secondRequestQueryParameters = new LinkedMultiValueMap()
            secondRequestQueryParameters.addAll("includedIngredient", ["banana", "tomato"])

            def thirdRequestQueryParameters = new LinkedMultiValueMap()
            thirdRequestQueryParameters.addAll("includedIngredient", ["tomato", "egg", "tuna"])
        and:
            stubSpoonacularSearchRecipesWithOnlyIncludedIngredients(firstRequestQueryParameters)
            stubSpoonacularSearchRecipesWithOnlyIncludedIngredients(secondRequestQueryParameters)
            stubSpoonacularSearchRecipesWithOnlyIncludedIngredients(thirdRequestQueryParameters)
        when:
            def firstResponse = searchRecipes(firstRequestQueryParameters)
            def secondResponse = searchRecipes(secondRequestQueryParameters)
            def thirdResponse = searchRecipes(thirdRequestQueryParameters)
        and:
            def popularIngredients = httpClient.getForEntity("/ingredients/popular", List.class)
        then:
            firstResponse.statusCode.'2xxSuccessful'
            secondResponse.statusCode.'2xxSuccessful'
            thirdResponse.statusCode.'2xxSuccessful'

            popularIngredients.body == ['banana', 'tomato', 'chocolate', 'egg', 'tuna']
    }

    private ResponseEntity<SearchRecipesResponse> searchRecipes(LinkedMultiValueMap queryParameters) {
        return httpClient.getForEntity(
            queryParamsWithOnlyIncludedIngredientsToImHungry(localUrl() + SEARCH_RECIPES_PATH, queryParameters), SearchRecipesResponse.class
        )
    }
}
