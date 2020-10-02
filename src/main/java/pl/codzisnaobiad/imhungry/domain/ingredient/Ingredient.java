package pl.codzisnaobiad.imhungry.domain.ingredient;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public final class Ingredient implements Comparable<Ingredient> {

    @Id
    private final String id;
    private final String name;
    private final Integer count;

    public Ingredient(String id, String name, int count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    @Override
    public int compareTo(Ingredient other) {
        return count.compareTo(other.count);
    }

    public static Ingredient from(String name) {
        return new Ingredient(null, name, 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, count);
    }
}
