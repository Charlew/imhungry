package pl.codzisnaobiad.imhungry.api.response.recipe;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
@JsonDeserialize(builder = Ingredient.Builder.class)
public final class Ingredient {
    private final String id;
    private final String name;
    private final float amount;
    private final String unit;
    private final String image;

    private Ingredient(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.amount = builder.amount;
        this.unit = builder.unit;
        this.image = builder.image;
    }

    public String getId() {
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

    public String getImage() {
        return image;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Float.compare(that.amount, amount) == 0 &&
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(unit, that.unit) &&
            Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, amount, unit, image);
    }

    @Override
    public String toString() {
        return "Ingredient{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", amount=" + amount +
            ", unit='" + unit + '\'' +
            ", image='" + image + '\'' +
            '}';
    }

    @JsonPOJOBuilder
    public static final class Builder {
        private String id;
        private String name;
        private float amount;
        private String unit;
        private String image;

        private Builder() {
        }

        public Builder withId(String id) {
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

        public Builder withImage(String imageUrl) {
            this.image = imageUrl;
            return this;
        }

        public Ingredient build() {
            return new Ingredient(this);
        }
    }
}
