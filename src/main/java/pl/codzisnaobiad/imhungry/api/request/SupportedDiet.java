package pl.codzisnaobiad.imhungry.api.request;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

enum SupportedDiet {

    GLUTEN_FREE("GLUTEN FREE"),
    KETOGENIC,
    VEGETARIAN,
    LACTO_VEGETARIAN("LACTO VEGETARIAN"),
    OVO_VEGETARIAN("OVO VEGETARIAN"),
    VEGAN,
    PESCETARIAN,
    PALEO,
    PRIMAL,
    WHOLE30;

    private final String value;

    private static final Map<String, SupportedDiet> nameToValueMap = new HashMap<>();

    static {
        for (SupportedDiet element : EnumSet.allOf(SupportedDiet.class)) {
            nameToValueMap.put(element.getValue(), element);
        }
    }

    static void assertIsValid(String diet) {
        var otherValueLowerCase = diet.toUpperCase();

        if (nameToValueMap.get(otherValueLowerCase) == null) {
            throw new IllegalArgumentException("Value '" + diet + "' is not valid supported diet");
        }
    }

    SupportedDiet() {
        this.value = null;
    }

    SupportedDiet(String value) {
        this.value = value;
    }

    public String getValue() {
        return Objects.requireNonNullElseGet(value, this::name);
    }

}
