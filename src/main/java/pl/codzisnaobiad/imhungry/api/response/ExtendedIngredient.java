package pl.codzisnaobiad.imhungry.api.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = ExtendedIngredient.Builder.class)
public final class ExtendedIngredient {
    private final int id;
    private final String name;
    private final float amount;
    private final String unit;
    private final String original;

    ExtendedIngredient(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.amount = builder.amount;
        this.unit = builder.unit;
        this.original = builder.original;
    }

    public int getId() {
        return id;
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

    public String getOriginal() {
        return original;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static final class Builder {
        private int id;
        private String name;
        private float amount;
        private String unit;
        private String original;

        private Builder() {
        }

        public static Builder anExtendedIngredient() {
            return new Builder();
        }

        public Builder withId(int id) {
            this.id = id;
            return this;
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

        public Builder withOriginal(String original) {
            this.original = original;
            return this;
        }

        public ExtendedIngredient build() {
            return new ExtendedIngredient(this);
        }
    }
}
