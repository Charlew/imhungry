package pl.codzisnaobiad.imhungry.api.response.recipe;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.Objects;

@JsonDeserialize(builder = Nutrient.Builder.class)
public final class Nutrient {
    private final String name;
    private final float amount;
    private final String unit;

    private Nutrient(Builder builder) {
        this.name = builder.name;
        this.amount = builder.amount;
        this.unit = builder.unit;
    }

    public String getName() {
        return name;
    }

    public float getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nutrient nutrient = (Nutrient) o;
        return Float.compare(nutrient.amount, amount) == 0 &&
            Objects.equals(name, nutrient.name) &&
            Objects.equals(unit, nutrient.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, amount, unit);
    }

    @Override
    public String toString() {
        return "Nutrient{" +
            "name='" + name + '\'' +
            ", amount=" + amount +
            ", unit='" + unit + '\'' +
            '}';
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static final class Builder {
        private String name;
        private float amount;
        private String unit;

        private Builder() {
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withAmount(float amount) {
            this.amount = amount;
            return this;
        }

        public Builder withUnit(String unit) {
            this.unit = unit;
            return this;
        }

        public Nutrient build() {
            return new Nutrient(this);
        }
    }
}
