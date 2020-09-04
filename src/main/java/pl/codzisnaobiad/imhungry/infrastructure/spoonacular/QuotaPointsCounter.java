package pl.codzisnaobiad.imhungry.infrastructure.spoonacular;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
class QuotaPointsCounter {

    private final AtomicInteger pointsCount;
    private final int quotaPointsLimit;

    QuotaPointsCounter(@Value("${client.spoonacular.quota-points-limit:149}") int quotaPointsLimit) {
        this.quotaPointsLimit = quotaPointsLimit;
        this.pointsCount = new AtomicInteger();
    }

    public void setQuotaPoints(int pointsCount) {
        this.pointsCount.set(pointsCount);
    }

    public boolean quotaPointsExceeded() {
        return pointsCount.get() >= quotaPointsLimit;
    }

}