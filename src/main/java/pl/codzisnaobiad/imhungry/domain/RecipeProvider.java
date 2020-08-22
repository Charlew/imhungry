package pl.codzisnaobiad.imhungry.domain;

import pl.codzisnaobiad.imhungry.api.RecipeResponse;

import java.util.List;

public interface RecipeProvider {

    List<RecipeResponse> getRecipes(List<String> ingredients, int maxRecipes);

}
