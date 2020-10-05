package pl.codzisnaobiad.imhungry.domain.ingredient;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

interface IngredientRepository extends Repository<Ingredient, String> {
    List<Ingredient> findTop10ByOrderByCountDesc();
    Optional<Ingredient> findByName(String name);
    Ingredient save(Ingredient ingredient);
}
