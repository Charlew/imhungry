package pl.codzisnaobiad.imhungry.domain;

import java.util.List;
import java.util.Random;

class DefaultRandomizer<T> implements Randomizer<T> {

    @Override
    public T randomize(List<T> elements) {
        Random random = new Random();
        var elementsCount = elements.size();
        return elements.get(random.nextInt(elementsCount));
    }

}
