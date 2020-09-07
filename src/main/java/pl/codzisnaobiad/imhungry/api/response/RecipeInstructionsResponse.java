package pl.codzisnaobiad.imhungry.api.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public final class RecipeInstructionsResponse {
    private final List<RecipeInstructionResponse> instructions;

    @JsonCreator
    public RecipeInstructionsResponse(@JsonProperty("instructions") List<RecipeInstructionResponse> instructions) {
        this.instructions = instructions;
    }

    public List<RecipeInstructionResponse> getInstructions() {
        return instructions;
    }
}
