package pl.codzisnaobiad.imhungry.domain;

import java.util.List;

public interface Randomizer<T> {

    T randomize(List<T> elements);

}
