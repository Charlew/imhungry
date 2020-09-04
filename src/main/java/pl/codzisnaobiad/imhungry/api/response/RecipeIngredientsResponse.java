package pl.codzisnaobiad.imhungry.api.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.List;

@JsonDeserialize(builder = RecipeIngredientsResponse.Builder.class)
public class RecipeIngredientsResponse {
    private final List<Ingredient> ingredients;
    private final int readyInMinutes;
    private final int servings;
    private final List<Nutrient> nutrients;

    public RecipeIngredientsResponse(Builder builder) {
        this.ingredients = builder.ingredients;
        this.readyInMinutes = builder.readyInMinutes;
        this.servings = builder.servings;
        this.nutrients = builder.nutrients;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public int getServings() {
        return servings;
    }

    public List<Nutrient> getNutrients() {
        return nutrients;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static final class Builder {
        private List<Ingredient> ingredients;
        private int readyInMinutes;
        private int servings;
        private List<Nutrient> nutrients;

        private Builder() {
        }

        public Builder withIngredients(List<Ingredient> ingredients) {
            this.ingredients = ingredients;
            return this;
        }

        public Builder withReadyInMinutes(int readyInMinutes) {
            this.readyInMinutes = readyInMinutes;
            return this;
        }

        public Builder withServings(int servings) {
            this.servings = servings;
            return this;
        }

        public Builder withNutrients(List<Nutrient> nutrients) {
            this.nutrients = nutrients;
            return this;
        }

        public RecipeIngredientsResponse build() {
            return new RecipeIngredientsResponse(this);
        }
    }
}
