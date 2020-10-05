package pl.codzisnaobiad.imhungry.api.response.recipe;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class SearchRecipeResponse {

    private final String id;
    private final String name;
    private final String imageUrl;

    @JsonCreator
    public SearchRecipeResponse(@JsonProperty("id") String id,
                                @JsonProperty("name") String name,
                                @JsonProperty("imageUrl") String imageUrl
    ) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchRecipeResponse that = (SearchRecipeResponse) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(imageUrl, that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, imageUrl);
    }

    @Override
    public String toString() {
        return "SearchRecipeResponse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

}
