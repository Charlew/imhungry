package pl.codzisnaobiad.imhungry.api.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Temperature that = (Temperature) o;
        return Float.compare(that.number, number) == 0 &&
            Objects.equals(unit, that.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, unit);
    }

    @Override
    public String toString() {
        return "Temperature{" +
            "number=" + number +
            ", unit='" + unit + '\'' +
            '}';
    }

}
