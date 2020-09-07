package pl.codzisnaobiad.imhungry.api.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public final class RecipeInstructionsResponse {
    private final List<RecipeInstructionResponse> instructions;

    @JsonCreator
    public RecipeInstructionsResponse(@JsonProperty("instructions") List<RecipeInstructionResponse> instructions) {
        this.instructions = instructions;
    }

    public List<RecipeInstructionResponse> getInstructions() {
        return instructions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeInstructionsResponse that = (RecipeInstructionsResponse) o;
        return Objects.equals(instructions, that.instructions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instructions);
    }

    @Override
    public String toString() {
        return "RecipeInstructionsResponse{" +
            "instructions=" + instructions +
            '}';
    }

}
