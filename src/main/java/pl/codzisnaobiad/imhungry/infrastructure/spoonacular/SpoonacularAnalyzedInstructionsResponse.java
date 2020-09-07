package pl.codzisnaobiad.imhungry.infrastructure.spoonacular;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
final class SpoonacularAnalyzedInstructionsResponse {
    private final String name;
    private final List<Step> steps;

    @JsonCreator
    SpoonacularAnalyzedInstructionsResponse(@JsonProperty("name") String name,
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

    @JsonIgnoreProperties(ignoreUnknown = true)
    static final class Step {
        private final int number;
        private final String step;
        private final List<Equipment> equipment;
        private final Length length;

        @JsonCreator
        Step(@JsonProperty("number") int number,
             @JsonProperty("step") String step,
             @JsonProperty("equipment") List<Equipment> equipment,
             @JsonProperty("length") Length length) {
            this.equipment = equipment;
            this.number = number;
            this.step = step;
            this.length = length;
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

        @JsonIgnoreProperties(ignoreUnknown = true)
        static final class Equipment {
            private final String name;
            private final String image;
            private final Temperature temperature;

            @JsonCreator
            Equipment(@JsonProperty("name") String name,
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

            @JsonIgnoreProperties(ignoreUnknown = true)
            static final class Temperature {
                private final String unit;
                private final float number;

                @JsonCreator
                Temperature(@JsonProperty("unit") String unit,
                            @JsonProperty("number") float number) {
                    this.unit = unit;
                    this.number = number;
                }

                public String getUnit() {
                    return unit;
                }

                public float getNumber() {
                    return number;
                }
            }
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        static final class Length {
            private final int number;
            private final String unit;

            @JsonCreator
            Length(@JsonProperty("number") int number,
                   @JsonProperty("unit") String unit) {
                this.number = number;
                this.unit = unit;
            }

            public int getNumber() {
                return number;
            }

            public String getUnit() {
                return unit;
            }
        }
    }
}
