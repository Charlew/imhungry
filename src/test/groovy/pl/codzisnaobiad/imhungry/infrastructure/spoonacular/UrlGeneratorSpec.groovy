package pl.codzisnaobiad.imhungry.infrastructure.spoonacular

import spock.lang.Specification
import spock.lang.Subject

class UrlGeneratorSpec extends Specification {

    @Subject
    UrlGenerator urlGenerator = new UrlGenerator()

    def "should generate ingredient image url"() {
        given:
            def imageUrlSuffix = "apple.jpg"

        when:
            def result = urlGenerator.generateIngredientImageUrl(imageUrlSuffix)

        then:
            result == "https://spoonacular.com/cdn/ingredients_250x250/apple.jpg"
    }

    def "should generate equipment image url"() {
        given:
            def imageUrlSuffix = "pan.jpg"

        when:
            def result = urlGenerator.generateEquipmentImageUrl(imageUrlSuffix)

        then:
            result == "https://spoonacular.com/cdn/equipment_250x250/pan.jpg"
    }

}
