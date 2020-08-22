package pl.codzisnaobiad.imhungry.domain;

import java.util.List;

interface Randomizer<T> {

    T randomize(List<T> elements);

}
