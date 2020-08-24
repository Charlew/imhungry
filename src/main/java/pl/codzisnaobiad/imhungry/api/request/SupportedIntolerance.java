package pl.codzisnaobiad.imhungry.api.request;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum SupportedIntolerance {

    DAIRY,
    EGG,
    GLUTEN,
    GRAIN,
    PEANUT,
    SEAFOOD,
    SESAME,
    SHELLFISH,
    SOY,
    SULFITE,
    WHEAT;

    private static final Map<String, SupportedIntolerance> nameToValueMap = new HashMap<>();

    static {
        for (SupportedIntolerance element : EnumSet.allOf(SupportedIntolerance.class)) {
            nameToValueMap.put(element.name(), element);
        }
    }

    static void assertIsValid(String intolerance) {
        var otherValueLowerCase = intolerance.toUpperCase();

        if (nameToValueMap.get(otherValueLowerCase) == null) {
            throw new IllegalArgumentException("Value '" + intolerance + "' is not valid supported intolerance");
        }
    }

}
