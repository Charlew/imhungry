package pl.codzisnaobiad.imhungry.infrastructure.spoonacular;

import org.springframework.stereotype.Component;

@Component
class QuotaPointsCounter {
    private int pointsCount;

    public int getPointsCount() {
        return pointsCount;
    }

    public void setQuotaPoints(int pointsCount) {
        this.pointsCount = pointsCount;
    }
}