package pl.codzisnaobiad.imhungry.api

import org.springframework.http.HttpStatus
import pl.codzisnaobiad.imhungry.IntegrationSpec

class RecipeEndpointSpec extends IntegrationSpec {

    def "should get random recipe"() {
        given:
        def ingredients = ["bananas", "chocolate"]

        and:
        stubSpoonacularSearchRecipesByIngredients(ingredients, "SpoonacularSearchRecipesResponse.json")

        when:
        def response = get("/recipes/random?ingredient=${ingredients[0]}&ingredient=${ingredients[1]}", RecipeResponse.class)

        then:
        response.statusCode == HttpStatus.OK
        with(response.body) {
            name
            imageUrl
            ingredients.size() != 0
        }
    }

}
