package pl.codzisnaobiad.imhungry.api.request;

enum RecipeQueryParameter {

    INCLUDED_INGREDIENT("includedIngredient"),
    EXCLUDED_INGREDIENT("excludedIngredient"),
    INTOLERANCE("intolerance"),
    NAME_QUERY("nameQuery"),
    DIET("diet"),
    MEAL_TYPE("mealType"),
    SORT_BY("sortBy");

    private final String value;

    RecipeQueryParameter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
