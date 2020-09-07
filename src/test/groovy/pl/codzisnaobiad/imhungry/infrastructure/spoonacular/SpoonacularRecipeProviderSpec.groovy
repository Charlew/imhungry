package pl.codzisnaobiad.imhungry.infrastructure.spoonacular

import pl.codzisnaobiad.imhungry.api.request.RecipeRequestModel
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class SpoonacularRecipeProviderSpec extends Specification {

    @Subject
    private SpoonacularRecipeProvider spoonacularRecipeProvider
    private SpoonacularClient spoonacularClient = Mock()
    private SpoonacularMapper spoonacularMapper = Mock()

    @Unroll
    def 'should return fake recipes when used quota points is equal #quotaPoints and limit is #quotaPointsLimit'() {
        given:
            def quotaPointsCounter = new QuotaPointsCounter(quotaPointsLimit)

        and:
            spoonacularRecipeProvider = new SpoonacularRecipeProvider(new FakeRecipeProvider(), spoonacularClient, quotaPointsCounter, spoonacularMapper)
            quotaPointsCounter.setQuotaPoints(quotaPoints)

        and:
            def recipeRequestModel = RecipeRequestModel.builder().withIncludedIngredients(List.of("bananas", "chocolate")).build()

        when:
            def result = spoonacularRecipeProvider.searchRecipes(recipeRequestModel)

        then:
            result.getRecipes()[0].id == '123'
            result.getRecipes()[0].name == 'Polish kebab'
            result.getRecipes()[0].imageUrl == 'kebab.jpg'

        where:
            quotaPointsLimit | quotaPoints
            149              | 150
            1                | 2
    }

    @Unroll
    def 'should return fake recipe ingredients when used quota points is equal #quotaPoints and limit is #quotaPointsLimit'() {
        given:
            def quotaPointsCounter = new QuotaPointsCounter(quotaPointsLimit)

        and:
            spoonacularRecipeProvider = new SpoonacularRecipeProvider(new FakeRecipeProvider(), spoonacularClient, quotaPointsCounter, spoonacularMapper)
            quotaPointsCounter.setQuotaPoints(quotaPoints)

        and:
            def recipeId = "123"

        when:
            def result = spoonacularRecipeProvider.getRecipeIngredients(recipeId)

        then:
            result.getIngredients().size() == 1
            result.readyInMinutes == 10
            result.servings == 1
            result.getNutrients().size() == 1

        where:
            quotaPointsLimit | quotaPoints
            149              | 150
            1                | 2
    }

}
