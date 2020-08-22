package pl.codzisnaobiad.imhungry.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public final class Ingredient {

    private final String name;
    private final String imageUrl;
    private final float amount;
    private final String unit;

    public Ingredient(@JsonProperty("name") String name,
                      @JsonProperty("imageUrl") String imageUrl,
                      @JsonProperty("amount") float amount,
                      @JsonProperty("unit") String unit
    ) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.amount = amount;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public float getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Float.compare(that.amount, amount) == 0 &&
                Objects.equals(name, that.name) &&
                Objects.equals(imageUrl, that.imageUrl) &&
                Objects.equals(unit, that.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, imageUrl, amount, unit);
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", amount=" + amount +
                ", unit='" + unit + '\'' +
                '}';
    }

}
