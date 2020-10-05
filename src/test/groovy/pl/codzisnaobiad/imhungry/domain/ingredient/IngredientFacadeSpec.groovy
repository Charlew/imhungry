package pl.codzisnaobiad.imhungry.domain.ingredient

import spock.lang.Specification
import spock.lang.Subject

class IngredientFacadeSpec extends Specification {

    @Subject
    IngredientFacade ingredientFacade
    IngredientRepository ingredientRepository

    def setup() {
        ingredientRepository = new InMemoryIngredientRepository()
        ingredientFacade = new IngredientFacade(ingredientRepository)
    }

    def "should get popular ingredients"() {
        given:
            def tomato = new Ingredient("uuid1", "tomato", 4)
            def banana = new Ingredient("uuid2", "banana", 3)
            def orange = new Ingredient("uuid3", "orange", 2)
            ingredientRepository.save(tomato)
            ingredientRepository.save(banana)
            ingredientRepository.save(orange)
        when:
            def result = ingredientFacade.getPopularIngredients()
        then:
            result == [
                "tomato", "banana", "orange"
            ]
    }

    def "should find ingredient by name and replace if exists"() {
        given:
            def name = "tomato"
            def ingredient = new Ingredient("uuid1", name, 5)
            ingredientRepository.save(ingredient)
        when:
            def result = ingredientFacade.saveIngredient(name)
        then:
            result.id == "uuid1"
            result.name == "tomato"
            result.count == 6
    }

    def "should create new ingredient"() {
        given:
            def name = "tomato"
        when:
            def result = ingredientFacade.saveIngredient(name)
        then:
            result.name == "tomato"
            result.count == 1
    }
}
