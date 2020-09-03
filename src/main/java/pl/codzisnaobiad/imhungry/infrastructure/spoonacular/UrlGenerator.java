package pl.codzisnaobiad.imhungry.infrastructure.spoonacular;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
class UrlGenerator {

    private static final String BASE_IMAGE_URL = "https://spoonacular.com/cdn/ingredients_";
    private static final String DEFAULT_IMAGE_SIZE = "250x250";

    String generateImageUrl(String imageUrlSuffix) {
        return UriComponentsBuilder
            .fromHttpUrl(BASE_IMAGE_URL)
            .path(DEFAULT_IMAGE_SIZE)
            .pathSegment(imageUrlSuffix)
            .encode()
            .build()
            .toString();
    }

}
