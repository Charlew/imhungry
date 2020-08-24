package pl.codzisnaobiad.imhungry.api.request;

import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.Set;

import static java.util.Collections.emptySet;
import static java.util.Optional.ofNullable;
import static pl.codzisnaobiad.imhungry.api.request.RecipeQueryParameter.INCLUDED_INGREDIENT;
import static pl.codzisnaobiad.imhungry.api.request.RecipeQueryParameter.EXCLUDED_INGREDIENT;
import static pl.codzisnaobiad.imhungry.api.request.RecipeQueryParameter.INTOLERANCE;
import static pl.codzisnaobiad.imhungry.api.request.RecipeQueryParameter.NAME_QUERY;
import static pl.codzisnaobiad.imhungry.api.request.RecipeQueryParameter.DIET;
import static pl.codzisnaobiad.imhungry.api.request.RecipeQueryParameter.MEAL_TYPE;
import static pl.codzisnaobiad.imhungry.api.request.RecipeQueryParameter.SORT_BY;

@Component
public class RecipeRequestModelMapper {

    public RecipeRequestModel toRequestModel(MultiValueMap<String, String> queryParameters) {
        return RecipeRequestModel.builder()
                .withIncludedIngredients(extractValuesAsSet(queryParameters, INCLUDED_INGREDIENT.getValue()))
                .withExcludedIngredients(extractValuesAsSet(queryParameters, EXCLUDED_INGREDIENT.getValue()))
                .withIntolerances(extractValuesAsSet(queryParameters, INTOLERANCE.getValue()))
                .withNameQuery(queryParameters.getFirst(NAME_QUERY.getValue()))
                .withDiet(queryParameters.getFirst(DIET.getValue()))
                .withMealType(queryParameters.getFirst(MEAL_TYPE.getValue()))
                .withSortBy(queryParameters.getFirst(SORT_BY.getValue()))
                .build();
    }

    private Set<String> extractValuesAsSet(MultiValueMap<String, String> queryParameters, String key) {
        return ofNullable(queryParameters.get(key)).map(Set::copyOf).orElse(emptySet());
    }

}
