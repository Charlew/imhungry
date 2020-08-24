package pl.codzisnaobiad.imhungry.infrastructure.spoonacular


import pl.codzisnaobiad.imhungry.api.Ingredient
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class SpoonacularRecipeProviderSpec extends Specification {

    @Subject
    private SpoonacularRecipeProvider spoonacularRecipeProvider
    private SpoonacularClient spoonacularClient = Mock()
    private QuotaPointsCounter quotaPointsCounter = new QuotaPointsCounter()

    @Unroll
    def 'should return fake recipe response when used quota points is equal #quotaPoints and limit is #quotaPointsLimit'() {
        given:
            spoonacularRecipeProvider = new SpoonacularRecipeProvider(spoonacularClient, quotaPointsCounter, quotaPointsLimit)
            quotaPointsCounter.setQuotaPoints(quotaPoints)
        when:
            def result = spoonacularRecipeProvider.getRecipes(_ as List<String>, 5)
        then:
            result[0].name =='Polish kebab'
            result[0].imageUrl == 'test'
            result[0].ingredients == [new Ingredient("Mutton", "test", 150f, "g"),
                                      new Ingredient("Salad", "test", 50f, "g"),
                                      new Ingredient("Garlic sauce", "test", 50f, "g")]
        where:
            quotaPointsLimit | quotaPoints
            149              | 150
            1                | 2
    }
}
