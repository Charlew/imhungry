package pl.codzisnaobiad.imhungry.domain.ingredient

import static java.util.stream.Collectors.toList

class InMemoryIngredientRepository implements IngredientRepository {
    private final Map<String, Ingredient> map = new HashMap<>();

    @Override
    Optional<List<Ingredient>> findTop10ByOrderByCountDesc() {
        return Optional.of(map.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
            .limit(10)
            .map({ entry -> entry.value })
            .collect(toList()))
    }

    @Override
    Optional<Ingredient> findByName(String name) {
        return Optional.ofNullable(map.get(name))
    }

    @Override
    Ingredient save(Ingredient ingredient) {
        map.put(ingredient.getName(), ingredient)
        return map.get(ingredient.getName())
    }
}
