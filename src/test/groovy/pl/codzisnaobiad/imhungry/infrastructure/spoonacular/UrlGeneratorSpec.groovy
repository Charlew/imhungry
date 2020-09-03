package pl.codzisnaobiad.imhungry.infrastructure.spoonacular

import spock.lang.Specification
import spock.lang.Subject

class UrlGeneratorSpec extends Specification {

    @Subject
    UrlGenerator urlGenerator = new UrlGenerator()

    def "should generate image url"() {
        given:
            def imageUrlSuffix = "apple.jpg"

        when:
            def result = urlGenerator.generateImageUrl(imageUrlSuffix)

        then:
            result == "https://spoonacular.com/cdn/ingredients_250x250/apple.jpg"
    }

}
