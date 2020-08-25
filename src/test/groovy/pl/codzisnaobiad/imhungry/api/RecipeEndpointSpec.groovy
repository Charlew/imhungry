package pl.codzisnaobiad.imhungry.api

import org.springframework.http.HttpStatus
import org.springframework.util.LinkedMultiValueMap
import pl.codzisnaobiad.imhungry.IntegrationSpec
import pl.codzisnaobiad.imhungry.api.response.SearchRecipesResponse

class RecipeEndpointSpec extends IntegrationSpec {

    def "should get random recipe"() {
        given:
            def queryParameters = new LinkedMultiValueMap()
            queryParameters.addAll("includedIngredient", ["banana", "chocolate"])
            queryParameters.add("excludedIngredient", "peanuts")
            queryParameters.add("intolerance", "gluten")
            queryParameters.add("nameQuery", "Chocolate")
            queryParameters.add("diet", "pescetarian")
            queryParameters.add("mealType", "dessert")

        and:
            stubSpoonacularSearchRecipes(queryParameters, "SpoonacularSearchRecipesResponse.json")

        when:
            def response = get("/recipes/search", queryParameters, SearchRecipesResponse.class)

        then:
            response.statusCode == HttpStatus.OK
            response.body.recipes.size() != 0
    }

}
