package pl.codzisnaobiad.imhungry.api.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = Ingredient.Builder.class)
public final class Ingredient {
    private final int id;
    private final String name;
    private final float amount;
    private final String unit;
    private final String imageUrl;

    Ingredient(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.amount = builder.amount;
        this.unit = builder.unit;
        this.imageUrl = builder.imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
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
        private String imageUrl;

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

        public Builder withImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Ingredient build() {
            return new Ingredient(this);
        }
    }
}
