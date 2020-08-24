package pl.codzisnaobiad.imhungry.api.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipesResponse {

    private final List<RecipeResponse> recipes;

    @JsonCreator
    public RecipesResponse(@JsonProperty("recipes") List<RecipeResponse> recipes) {
        this.recipes = recipes;
    }

    public List<RecipeResponse> getRecipes() {
        return recipes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipesResponse that = (RecipesResponse) o;
        return Objects.equals(recipes, that.recipes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipes);
    }

    @Override
    public String toString() {
        return "RecipesResponse{" +
                "recipes=" + recipes +
                '}';
    }

}
