package pl.codzisnaobiad.imhungry.api.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Temperature {
    private final float number;
    private final String unit;

    @JsonCreator
    public Temperature(@JsonProperty("number") float number,
                       @JsonProperty("unit") String unit) {
        this.number = number;
        this.unit = unit;
    }

    public float getNumber() {
        return number;
    }

    public String getUnit() {
        return unit;
    }
}
