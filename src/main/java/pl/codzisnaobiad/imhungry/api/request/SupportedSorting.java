package pl.codzisnaobiad.imhungry.api.request;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum SupportedSorting {

    POPULARITY,
    HEALTHINESS,
    PRICE,
    TIME,
    CALORIES;

    private static final Map<String, SupportedSorting> nameToValueMap = new HashMap<>();

    static {
        for (SupportedSorting element : EnumSet.allOf(SupportedSorting.class)) {
            nameToValueMap.put(element.name(), element);
        }
    }

    static void assertIsValid(String sorting) {
        var otherValueLowerCase = sorting.toUpperCase();

        if (nameToValueMap.get(otherValueLowerCase) == null) {
            throw new IllegalArgumentException("Value '" + sorting + "' is not valid supported sorting");
        }
    }

}
