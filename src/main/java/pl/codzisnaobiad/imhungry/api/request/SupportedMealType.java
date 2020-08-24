package pl.codzisnaobiad.imhungry.api.request;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

enum SupportedMealType {

    MAIN_COURSE("MAIN COURSE"),
    SIDE_DISH("SIDE DISH"),
    DESSERT,
    APPETIZER,
    SALAD,
    BREAD,
    BREAKFAST,
    SOUP,
    BEVERAGE,
    SAUCE,
    MARINADE,
    FINGERFOOD,
    SNACK,
    DRINK;

    private final String value;

    private static final Map<String, SupportedMealType> nameToValueMap = new HashMap<>();

    static {
        for (SupportedMealType element : EnumSet.allOf(SupportedMealType.class)) {
            nameToValueMap.put(element.getValue(), element);
        }
    }

    static void assertIsValid(String mealType) {
        var otherValueLowerCase = mealType.toUpperCase();

        if (nameToValueMap.get(otherValueLowerCase) == null) {
            throw new IllegalArgumentException("Value '" + mealType + "' is not valid supported meal type");
        }
    }

    SupportedMealType() {
        this.value = null;
    }

    SupportedMealType(String value) {
        this.value = value;
    }

    public String getValue() {
        return Objects.requireNonNullElseGet(value, this::name);
    }

}
