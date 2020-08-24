package pl.codzisnaobiad.imhungry.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.Assert;

import java.util.Set;
import java.util.Objects;

public class RecipeRequestModel {

    private final Set<String> includedIngredients;
    private final Set<String> excludedIngredients;
    private final Set<String> intolerances;
    private final String nameQuery;
    private final String diet;
    private final String mealType;
    private final String sortBy;

    @JsonCreator
    public RecipeRequestModel(
            @JsonProperty("includedIngredients") Set<String> includedIngredients,
            @JsonProperty("excludedIngredients") Set<String> excludedIngredients,
            @JsonProperty("intolerances") Set<String> intolerances,
            @JsonProperty("nameQuery") String nameQuery,
            @JsonProperty("diet") String diet,
            @JsonProperty("mealType") String mealType,
            @JsonProperty("sortBy") String sortBy
    ) {
        this.includedIngredients = includedIngredients;
        this.excludedIngredients = excludedIngredients;
        this.intolerances = intolerances;
        this.nameQuery = nameQuery;
        this.diet = diet;
        this.mealType = mealType;
        this.sortBy = sortBy;
    }

    public Set<String> getIncludedIngredients() {
        return includedIngredients;
    }

    public Set<String> getExcludedIngredients() {
        return excludedIngredients;
    }

    public Set<String> getIntolerances() {
        return intolerances;
    }

    public String getNameQuery() {
        return nameQuery;
    }

    public String getDiet() {
        return diet;
    }

    public String getMealType() {
        return mealType;
    }

    public String getSortBy() {
        return sortBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeRequestModel that = (RecipeRequestModel) o;
        return Objects.equals(includedIngredients, that.includedIngredients) &&
                Objects.equals(excludedIngredients, that.excludedIngredients) &&
                Objects.equals(intolerances, that.intolerances) &&
                Objects.equals(nameQuery, that.nameQuery) &&
                Objects.equals(diet, that.diet) &&
                Objects.equals(mealType, that.mealType) &&
                Objects.equals(sortBy, that.sortBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(includedIngredients, excludedIngredients, intolerances, nameQuery, diet, mealType, sortBy);
    }

    @Override
    public String toString() {
        return "RecipeRequestModel{" +
                "includedIngredients=" + includedIngredients +
                ", excludedIngredients=" + excludedIngredients +
                ", intolerances=" + intolerances +
                ", nameQuery='" + nameQuery + '\'' +
                ", diet='" + diet + '\'' +
                ", mealType='" + mealType + '\'' +
                ", sortBy='" + sortBy + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private Set<String> includedIngredients;
        private Set<String> excludedIngredients;
        private Set<String> intolerances;
        private String nameQuery;
        private String diet;
        private String mealType;
        private String sortBy;

        private Builder() {
        }

        public Builder withIncludedIngredients(Set<String> includedIngredients) {
            Assert.notEmpty(includedIngredients, "At least one included ingredient is required");
            this.includedIngredients = includedIngredients;
            return this;
        }

        public Builder withExcludedIngredients(Set<String> excludedIngredients) {
            this.excludedIngredients = excludedIngredients;
            return this;
        }

        public Builder withIntolerances(Set<String> intolerances) {
            if (intolerances != null) {
                for (String intolerance: intolerances ) {
                    SupportedIntolerance.assertIsValid(intolerance);
                }
            }
            this.intolerances = intolerances;
            return this;
        }

        public Builder withNameQuery(String nameQuery) {
            this.nameQuery = nameQuery;
            return this;
        }

        public Builder withDiet(String diet) {
            if (diet != null) {
                SupportedDiet.assertIsValid(diet);
            }
            this.diet = diet;
            return this;
        }

        public Builder withMealType(String mealType) {
            if (mealType != null) {
                SupportedMealType.assertIsValid(mealType);
            }
            this.mealType = mealType;
            return this;
        }

        public Builder withSortBy(String sortBy) {
            if (sortBy != null) {
                SupportedSorting.assertIsValid(sortBy);
            }
            this.sortBy = sortBy;
            return this;
        }

        public RecipeRequestModel build() {
            return new RecipeRequestModel(includedIngredients, excludedIngredients, intolerances, nameQuery, diet, mealType, sortBy);
        }

    }

}
