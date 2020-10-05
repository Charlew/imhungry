package pl.codzisnaobiad.imhungry.domain.ingredient;

import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class IngredientFacade {
    private final IngredientRepository ingredientRepository;

    public IngredientFacade(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public List<String> getPopularIngredients() {
        return ingredientRepository.findTop10ByOrderByCountDesc().stream()
            .map(Ingredient::getName)
            .collect(toList());
    }

    public Ingredient saveIngredient(String name) {
        return ingredientRepository.findByName(name)
            .map(ingredient -> ingredientRepository.save(new Ingredient(
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getCount() + 1))
            )
            .orElseGet(() -> ingredientRepository.save(Ingredient.from(name)));
    }
}