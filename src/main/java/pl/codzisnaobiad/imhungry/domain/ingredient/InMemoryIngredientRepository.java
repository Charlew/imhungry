package pl.codzisnaobiad.imhungry.domain.ingredient;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class InMemoryIngredientRepository implements IngredientRepository {
    private final Map<String, Ingredient> map = new HashMap<>();

    @Override
    public Optional<List<Ingredient>> findTop10ByOrderByCountDesc() {
        return Optional.of(map.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
            .limit(10)
            .map(Map.Entry::getValue)
            .collect(toList()));
    }

    @Override
    public Optional<Ingredient> findByName(String name) {
        return Optional.ofNullable(map.get(name));
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        map.put(ingredient.getName(), ingredient);
        return map.get(ingredient.getName());
    }
}
