package pl.codzisnaobiad.imhungry.infrastructure.spoonacular;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
class QuotaPointsCounter {
    private final AtomicInteger pointsCount;

    QuotaPointsCounter() {
        pointsCount = new AtomicInteger();
    }

    public int getPointsCount() {
        return pointsCount.get();
    }

    public void setQuotaPoints(int pointsCount) {
        this.pointsCount.set(pointsCount);
    }
}