package pl.codzisnaobiad.imhungry.infrastructure.spoonacular;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
class UrlGenerator {

    private static final String BASE_INGREDIENT_IMAGE_URL = "https://spoonacular.com/cdn/ingredients_";
    private static final String BASE_EQUIPMENT_IMAGE_URL = "https://spoonacular.com/cdn/equipment_";
    private static final String DEFAULT_IMAGE_SIZE = "250x250";

    String generateIngredientImageUrl(String imageUrlSuffix) {
        return UriComponentsBuilder
            .fromHttpUrl(BASE_INGREDIENT_IMAGE_URL)
            .path(DEFAULT_IMAGE_SIZE)
            .pathSegment(imageUrlSuffix)
            .encode()
            .build()
            .toString();
    }

    String generateEquipmentImageUrl(String imageUrlSuffix) {
        return UriComponentsBuilder
            .fromHttpUrl(BASE_EQUIPMENT_IMAGE_URL)
            .path(DEFAULT_IMAGE_SIZE)
            .pathSegment(imageUrlSuffix)
            .encode()
            .build()
            .toString();
    }

}
