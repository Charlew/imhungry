package pl.codzisnaobiad.imhungry.api.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

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
}
