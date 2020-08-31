package pl.codzisnaobiad.imhungry.api.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.List;

@JsonDeserialize(builder = RecipeInformationResponse.Builder.class)
public class RecipeInformationResponse {
    private final List<ExtendedIngredient> extendedIngredients;
    private final String title;
    private final int readyInMinutes;
    private final int servings;
    private final String sourceUrl;
    private final String imageUrl;
    private final List<Nutrient> nutrients;

    public RecipeInformationResponse(Builder builder) {
        this.extendedIngredients = builder.extendedIngredients;
        this.title = builder.title;
        this.readyInMinutes = builder.readyInMinutes;
        this.servings = builder.servings;
        this.sourceUrl = builder.sourceUrl;
        this.imageUrl = builder.imageUrl;
        this.nutrients = builder.nutrients;
    }

    public List<ExtendedIngredient> getExtendedIngredients() {
        return extendedIngredients;
    }

    public String getTitle() {
        return title;
    }

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public int getServings() {
        return servings;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<Nutrient> getNutrients() {
        return nutrients;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static final class Builder {
        private List<ExtendedIngredient> extendedIngredients;
        private String title;
        private int readyInMinutes;
        private int servings;
        private String sourceUrl;
        private String imageUrl;
        private List<Nutrient> nutrients;

        private Builder() {
        }

        public Builder withExtendedIngredients(List<ExtendedIngredient> extendedIngredients) {
            this.extendedIngredients = extendedIngredients;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
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

        public Builder withSourceUrl(String sourceUrl) {
            this.sourceUrl = sourceUrl;
            return this;
        }

        public Builder withImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder withNutrients(List<Nutrient> nutrients) {
            this.nutrients = nutrients;
            return this;
        }

        public RecipeInformationResponse build() {
            return new RecipeInformationResponse(this);
        }
    }
}
