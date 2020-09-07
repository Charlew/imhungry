package pl.codzisnaobiad.imhungry.api.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Length {
    private final int number;
    private final String unit;

    @JsonCreator
    public Length(@JsonProperty("number") int number,
                  @JsonProperty("unit") String unit) {
        this.number = number;
        this.unit = unit;
    }

    public int getNumber() {
        return number;
    }

    public String getUnit() {
        return unit;
    }
}
