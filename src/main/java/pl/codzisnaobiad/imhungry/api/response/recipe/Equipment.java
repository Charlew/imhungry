package pl.codzisnaobiad.imhungry.api.response.recipe;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipment equipment = (Equipment) o;
        return Objects.equals(name, equipment.name) &&
            Objects.equals(image, equipment.image) &&
            Objects.equals(temperature, equipment.temperature);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, image, temperature);
    }

    @Override
    public String toString() {
        return "Equipment{" +
            "name='" + name + '\'' +
            ", image='" + image + '\'' +
            ", temperature=" + temperature +
            '}';
    }

}
