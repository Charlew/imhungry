package pl.codzisnaobiad.imhungry.api.request

import org.springframework.util.LinkedMultiValueMap
import spock.lang.Specification
import spock.lang.Subject

class RecipeRequestModelMapperSpec extends Specification {

    @Subject
    RecipeRequestModelMapper recipeRequestModelMapper

    def setup() {
        recipeRequestModelMapper = new RecipeRequestModelMapper()
    }

    def "should map query parameters to recipe request model"() {
        given:
            def queryParameters = new LinkedMultiValueMap()
            queryParameters.addAll("includedIngredient", ["banana", "chocolate"])
            queryParameters.add("excludedIngredient", "peanuts")
            queryParameters.add("excludedIngredient", "peanuts")
            queryParameters.add("nameQuery", "ice cream")
            queryParameters.add("intolerance", "dairy")
            queryParameters.add("diet", "vegan")
            queryParameters.add("mealType", "dessert")
            queryParameters.add("sortBy", "time")

        when:
            def recipeRequestModel = recipeRequestModelMapper.toRequestModel(queryParameters)

        then:
            notThrown(Exception)

        and:
            recipeRequestModel.getIncludedIngredients() == Set.of("chocolate", "banana")
            recipeRequestModel.getExcludedIngredients() == Set.of("peanuts")
            recipeRequestModel.getIntolerances() == Set.of("dairy")
            recipeRequestModel.getNameQuery() == "ice cream"
            recipeRequestModel.getDiet() == "vegan"
            recipeRequestModel.getMealType() == "dessert"
            recipeRequestModel.getSortBy() == "time"
    }

    def "should require included ingredient in query parameters"() {
        given:
            def queryParameters = new LinkedMultiValueMap(Map.of("diet", "vegan"))

        when:
            recipeRequestModelMapper.toRequestModel(queryParameters)

        then:
            thrown(Exception)
    }

    def "should require only supported types for meal type"() {
        given:
            def queryParameters = new LinkedMultiValueMap(Map.of(
                    "includedIngredient", "banana",
                    "mealType", "notSupportedMealType"
            ))

        when:
            recipeRequestModelMapper.toRequestModel(queryParameters)

        then:
            thrown(Exception)
    }

    def "should require only supported types for intolerances"() {
        given:
            def queryParameters = new LinkedMultiValueMap(Map.of(
                    "includedIngredient", "banana",
                    "intolerances", "notSupportedIntolerance"
            ))

        when:
            recipeRequestModelMapper.toRequestModel(queryParameters)

        then:
            thrown(Exception)
    }

    def "should require only supported types for diet"() {
        given:
            def queryParameters = new LinkedMultiValueMap(Map.of(
                    "includedIngredient", "banana",
                    "diet", "notSupportedDiet"
            ))

        when:
            recipeRequestModelMapper.toRequestModel(queryParameters)

        then:
            thrown(Exception)
    }

    def "should require only supported types for sorting"() {
        given:
            def queryParameters = new LinkedMultiValueMap(Map.of(
                    "includedIngredient", "banana",
                    "sortBy", "notSupportedSorting"
            ))

        when:
            recipeRequestModelMapper.toRequestModel(queryParameters)

        then:
            thrown(Exception)
    }

}
