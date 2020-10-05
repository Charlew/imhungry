package pl.codzisnaobiad.imhungry.api.request

import pl.codzisnaobiad.imhungry.api.request.recipe.RecipeRequestModel
import spock.lang.Specification

class RecipeRequestModelSpec extends Specification {

    def "should map query parameters to recipe request model"() {
        given:
            def includedIngredients = ["banana", "chocolate"]
            def excludedIngredients = ["peanuts"]
            def intolerances = ["dairy"]
            def nameQuery = "ice cream"
            def diet = "vegan"
            def mealType = "dessert"

        when:
            def recipeRequestModel = RecipeRequestModel.builder()
                    .withIncludedIngredients(includedIngredients)
                    .withExcludedIngredients(excludedIngredients)
                    .withIntolerances(intolerances)
                    .withNameQuery(nameQuery)
                    .withDiet(diet)
                    .withMealType(mealType)
                    .build()

        then:
            notThrown(Exception)

        and:
            recipeRequestModel.getIncludedIngredients() == List.of("banana", "chocolate")
            recipeRequestModel.getExcludedIngredients() == List.of("peanuts")
            recipeRequestModel.getIntolerances() == List.of("dairy")
            recipeRequestModel.getNameQuery() == "ice cream"
            recipeRequestModel.getDiet() == "vegan"
            recipeRequestModel.getMealType() == "dessert"
    }

    def "should require only supported types for meal type"() {
        given:
            def includedIngredients = ["banana", "chocolate"]
            def mealType = "notSupportedMealType"

        when:
            RecipeRequestModel.builder()
                    .withIncludedIngredients(includedIngredients)
                    .withMealType(mealType)
                    .build()

        then:
            thrown(Exception)
    }

    def "should require only supported types for intolerances"() {
        given:
            def includedIngredients = ["banana", "chocolate"]
            def intolerances = ["notSupportedIntolerance"]

        when:
            RecipeRequestModel.builder()
                    .withIncludedIngredients(includedIngredients)
                    .withIntolerances(intolerances)
                    .build()

        then:
            thrown(Exception)
    }

    def "should require only supported types for diet"() {
        given:
            def includedIngredients = ["banana", "chocolate"]
            def diet = "notSupportedMealDiet"

        when:
            RecipeRequestModel.builder()
                    .withIncludedIngredients(includedIngredients)
                    .withDiet(diet)
                    .build()

        then:
            thrown(Exception)
    }

}
