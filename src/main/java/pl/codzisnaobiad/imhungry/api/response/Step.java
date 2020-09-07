package pl.codzisnaobiad.imhungry.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.List;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
@JsonDeserialize(builder = Step.Builder.class)
public final class Step {
    private final int number;
    private final String step;
    private final List<Equipment> equipment;
    private final Length length;

    public Step(Builder builder) {
        this.number = builder.number;
        this.step = builder.step;
        this.equipment = builder.equipment;
        this.length = builder.length;
    }

    public int getNumber() {
        return number;
    }

    public String getStep() {
        return step;
    }

    public List<Equipment> getEquipment() {
        return equipment;
    }

    public Length getLength() {
        return length;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Step step1 = (Step) o;
        return number == step1.number &&
            Objects.equals(step, step1.step) &&
            Objects.equals(equipment, step1.equipment) &&
            Objects.equals(length, step1.length);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, step, equipment, length);
    }

    @Override
    public String toString() {
        return "Step{" +
            "number=" + number +
            ", step='" + step + '\'' +
            ", equipment=" + equipment +
            ", length=" + length +
            '}';
    }

    @JsonPOJOBuilder
    public static final class Builder {
        private int number;
        private String step;
        private List<Equipment> equipment;
        private Length length;

        private Builder() {
        }

        public Builder withNumber(int number) {
            this.number = number;
            return this;
        }

        public Builder withStep(String step) {
            this.step = step;
            return this;
        }

        public Builder withEquipment(List<Equipment> equipment) {
            this.equipment = equipment;
            return this;
        }

        public Builder withLength(Length length) {
            this.length = length;
            return this;
        }

        public Step build() {
            return new Step(this);
        }
    }
}
