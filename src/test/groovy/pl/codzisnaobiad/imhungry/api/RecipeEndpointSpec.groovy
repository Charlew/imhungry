package pl.codzisnaobiad.imhungry.api

import org.springframework.http.HttpStatus
import org.springframework.util.LinkedMultiValueMap
import pl.codzisnaobiad.imhungry.IntegrationSpec
import pl.codzisnaobiad.imhungry.api.response.RecipeInformationResponse
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

    def "should get recipe information by recipe id"() {
        given:
            def id = "716429"
        and:
            stubSpoonacularGetRecipeInformationById(id, "SpoonacularGetRecipeInformationResponse.json")
        when:
            def response = httpClient.getForEntity("/recipes/$id/information", RecipeInformationResponse.class)
        then:
            response.statusCode.'2xxSuccessful'
            response.body.with {
                extendedIngredients.size() != 0
                nutrients.size() != 0
                imageUrl == "https://spoonacular.com/recipeImages/716429-556x370.jpg"
                readyInMinutes == 45
                servings == 2
                title == "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs"
                sourceUrl == "http://fullbellysisters.blogspot.com/2012/06/pasta-with-garlic-scallions-cauliflower.html"
            }

    }

}
