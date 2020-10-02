package pl.codzisnaobiad.imhungry.domain.ingredient;

import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Service
public class IngredientFacade {
    private final IngredientRepository ingredientRepository;

    public IngredientFacade(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public List<String> getPopularIngredients() {
        return ingredientRepository.findTop10ByOrderByCountDesc()
            .map(ingredients -> ingredients.stream()
                .map(Ingredient::getName)
                .collect(toList()))
            .orElse(emptyList());
    }

    public Ingredient findByNameAndReplace(String name) {
        return ingredientRepository.findByName(name)
            .map(ingredient -> ingredientRepository.save(new Ingredient(
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getCount() + 1))
            )
            .orElse(ingredientRepository.save(Ingredient.from(name)));
    }
}