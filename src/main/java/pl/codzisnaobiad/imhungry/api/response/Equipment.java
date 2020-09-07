package pl.codzisnaobiad.imhungry.api.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public final class Equipment {
    private final String name;
    private final String image;
    private final Temperature temperature;

    @JsonCreator
    public Equipment(@JsonProperty("name") String name,
                     @JsonProperty("image") String image,
                     @JsonProperty("temperature") Temperature temperature) {
        this.name = name;
        this.image = image;
        this.temperature = temperature;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Temperature getTemperature() {
        return temperature;
    }
}
