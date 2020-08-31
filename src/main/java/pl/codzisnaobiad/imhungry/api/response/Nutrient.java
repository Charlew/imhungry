package pl.codzisnaobiad.imhungry.api.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = Nutrient.Builder.class)
public final class Nutrient {
    private final String title;
    private final float amount;
    private final String unit;
    private final float percentOfDailyNeeds;

    Nutrient(Builder builder) {
        this.title = builder.title;
        this.amount = builder.amount;
        this.unit = builder.unit;
        this.percentOfDailyNeeds = builder.percentOfDailyNeeds;
    }

    public String getTitle() {
        return title;
    }

    public float getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    public float getPercentOfDailyNeeds() {
        return percentOfDailyNeeds;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static final class Builder {
        private String title;
        private float amount;
        private String unit;
        private float percentOfDailyNeeds;

        private Builder() {
        }


        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withAmount(float amount) {
            this.amount = amount;
            return this;
        }

        public Builder withUnit(String unit) {
            this.unit = unit;
            return this;
        }

        public Builder withPercentOfDailyNeeds(float percentOfDailyNeeds) {
            this.percentOfDailyNeeds = percentOfDailyNeeds;
            return this;
        }

        public Nutrient build() {
            return new Nutrient(this);
        }
    }
}
