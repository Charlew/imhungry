package pl.codzisnaobiad.imhungry.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public final class RecipeResponse {

    private final String name;
    private final String imageUrl;
    private final List<Ingredient> ingredients;

    @JsonCreator
    public RecipeResponse(@JsonProperty("name") String name,
                          @JsonProperty("imageUrl") String imageUrl,
                          @JsonProperty("ingredients") List<Ingredient> ingredients
    ) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeResponse recipe = (RecipeResponse) o;
        return Objects.equals(name, recipe.name) &&
                Objects.equals(imageUrl, recipe.imageUrl) &&
                Objects.equals(ingredients, recipe.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, imageUrl, ingredients);
    }

    @Override
    public String toString() {
        return "RecipeResponse{" +
                "name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }

}
