package pl.codzisnaobiad.imhungry.api.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchRecipesResponse {

    private final List<SearchRecipeResponse> recipes;

    @JsonCreator
    public SearchRecipesResponse(@JsonProperty("recipes") List<SearchRecipeResponse> recipes) {
        this.recipes = recipes;
    }

    public List<SearchRecipeResponse> getRecipes() {
        return recipes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchRecipesResponse that = (SearchRecipesResponse) o;
        return Objects.equals(recipes, that.recipes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipes);
    }

    @Override
    public String toString() {
        return "SearchRecipesResponse{" +
                "recipes=" + recipes +
                '}';
    }

}
