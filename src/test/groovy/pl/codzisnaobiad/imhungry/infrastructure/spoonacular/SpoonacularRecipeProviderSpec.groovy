package pl.codzisnaobiad.imhungry.infrastructure.spoonacular

import pl.codzisnaobiad.imhungry.api.request.RecipeRequestModel
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class SpoonacularRecipeProviderSpec extends Specification {

    @Subject
    private SpoonacularRecipeProvider spoonacularRecipeProvider
    private SpoonacularClient spoonacularClient = Mock()
    private QuotaPointsCounter quotaPointsCounter = new QuotaPointsCounter()
    private NutrientsPicker nutrientsPicker = new NutrientsPicker()
    private UrlGenerator urlGenerator = new UrlGenerator()

    @Unroll
    def 'should return fake recipe response when used quota points is equal #quotaPoints and limit is #quotaPointsLimit'() {
        given:
            spoonacularRecipeProvider = new SpoonacularRecipeProvider(spoonacularClient, quotaPointsCounter, nutrientsPicker, urlGenerator, quotaPointsLimit)
            quotaPointsCounter.setQuotaPoints(quotaPoints)
        and:
            def recipeRequestModel = RecipeRequestModel.builder().withIncludedIngredients(List.of("bananas", "chocolate")).build()

        when:
            def result = spoonacularRecipeProvider.searchRecipes(recipeRequestModel)

        then:
            result.getRecipes()[0].id == '123'
            result.getRecipes()[0].name == 'Polish kebab'
            result.getRecipes()[0].imageUrl == 'test'

        where:
            quotaPointsLimit | quotaPoints
            149              | 150
            1                | 2
    }

}
