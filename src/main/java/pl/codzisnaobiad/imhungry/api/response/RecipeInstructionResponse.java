package pl.codzisnaobiad.imhungry.api.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class RecipeInstructionResponse {
    private final String name;
    private final List<Step> steps;

    @JsonCreator
    public RecipeInstructionResponse(@JsonProperty("name") String name,
                                     @JsonProperty("steps") List<Step> steps) {
        this.name = name;
        this.steps = steps;
    }

    public String getName() {
        return name;
    }

    public List<Step> getSteps() {
        return steps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeInstructionResponse that = (RecipeInstructionResponse) o;
        return Objects.equals(name, that.name) &&
            Objects.equals(steps, that.steps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, steps);
    }

    @Override
    public String toString() {
        return "RecipeInstructionResponse{" +
            "name='" + name + '\'' +
            ", steps=" + steps +
            '}';
    }

}
