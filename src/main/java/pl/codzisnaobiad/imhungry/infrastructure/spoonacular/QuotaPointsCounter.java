package pl.codzisnaobiad.imhungry.infrastructure.spoonacular;

import org.springframework.stereotype.Component;

@Component
class QuotaPointsCounter {
    // TODO: 25/08/2020 zmienic na atomic integer
    private int pointsCount;

    public int getPointsCount() {
        return pointsCount;
    }

    public void setQuotaPoints(int pointsCount) {
        this.pointsCount = pointsCount;
    }
}