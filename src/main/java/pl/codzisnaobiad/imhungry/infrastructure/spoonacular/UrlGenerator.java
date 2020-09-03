package pl.codzisnaobiad.imhungry.infrastructure.spoonacular;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
class UrlGenerator {

    private static final String baseImageUrl = "https://spoonacular.com/cdn/ingredients_";
    private static final String defaultImageSize = "250x250";

    String generateImageUrl(String imageUrlSuffix) {
        return UriComponentsBuilder
            .fromHttpUrl(baseImageUrl)
            .path(defaultImageSize)
            .pathSegment(imageUrlSuffix)
            .encode()
            .build()
            .toString();
    }

}
