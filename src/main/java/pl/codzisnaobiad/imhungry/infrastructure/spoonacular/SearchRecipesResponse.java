package pl.codzisnaobiad.imhungry.infrastructure.spoonacular;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
final class SearchRecipesResponse {

    private final String title;
    private final String image;
    private final List<IngredientResponse> usedIngredients;
    private final List<IngredientResponse> unusedIngredients;

    @JsonCreator
    SearchRecipesResponse(@JsonProperty("title") String title,
                          @JsonProperty("image") String image,
                          @JsonProperty("usedIngredients") List<IngredientResponse> usedIngredients,
                          @JsonProperty("unusedIngredients") List<IngredientResponse> unusedIngredients
    ) {
        this.title = title;
        this.image = image;
        this.usedIngredients = usedIngredients;
        this.unusedIngredients = unusedIngredients;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public List<IngredientResponse> getUsedIngredients() {
        return usedIngredients;
    }

    public List<IngredientResponse> getUnusedIngredients() {
        return unusedIngredients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchRecipesResponse that = (SearchRecipesResponse) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(image, that.image) &&
                Objects.equals(usedIngredients, that.usedIngredients) &&
                Objects.equals(unusedIngredients, that.unusedIngredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, image, usedIngredients, unusedIngredients);
    }

    @Override
    public String toString() {
        return "SearchRecipesResponse{" +
                "title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", usedIngredients=" + usedIngredients +
                ", unusedIngredients=" + unusedIngredients +
                '}';
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static final class IngredientResponse {

        private final String name;
        private final String image;
        private final float amount;
        private final String unit;

        @JsonCreator
        private IngredientResponse(@JsonProperty("name") String name,
                                   @JsonProperty("image") String image,
                                   @JsonProperty("amount") float amount,
                                   @JsonProperty("unit") String unit
        ) {
            this.name = name;
            this.image = image;
            this.amount = amount;
            this.unit = unit;
        }

        public String getName() {
            return name;
        }

        public String getImage() {
            return image;
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
            IngredientResponse that = (IngredientResponse) o;
            return Float.compare(that.amount, amount) == 0 &&
                    Objects.equals(name, that.name) &&
                    Objects.equals(image, that.image) &&
                    Objects.equals(unit, that.unit);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, image, amount, unit);
        }

        @Override
        public String toString() {
            return "IngredientResponse{" +
                    "name='" + name + '\'' +
                    ", image='" + image + '\'' +
                    ", amount=" + amount +
                    ", unit='" + unit + '\'' +
                    '}';
        }

    }

}
