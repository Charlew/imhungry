package pl.codzisnaobiad.imhungry.infrastructure.spoonacular;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
final class SearchRecipesResponse {

    private final String id;
    private final String title;
    private final String image;

    @JsonCreator
    SearchRecipesResponse(@JsonProperty("id") String id,
                          @JsonProperty("title") String title,
                          @JsonProperty("image") String image
    ) {
        this.id = id;
        this.title = title;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchRecipesResponse that = (SearchRecipesResponse) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, image);
    }

    @Override
    public String toString() {
        return "SearchRecipesResponse{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

}
