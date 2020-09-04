package pl.codzisnaobiad.imhungry.api.exception;

import static java.lang.String.format;

public class SpoonacularCommunicationException extends RuntimeException {
    public SpoonacularCommunicationException(String message) {
        super(format("Communication with Spoonacular failed with message: %s", message));
    }
}
