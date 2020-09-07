package pl.codzisnaobiad.imhungry.api.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Length length = (Length) o;
        return number == length.number &&
            Objects.equals(unit, length.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, unit);
    }

    @Override
    public String toString() {
        return "Length{" +
            "number=" + number +
            ", unit='" + unit + '\'' +
            '}';
    }

}
