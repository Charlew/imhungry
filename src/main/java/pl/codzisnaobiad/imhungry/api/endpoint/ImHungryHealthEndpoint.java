package pl.codzisnaobiad.imhungry.api.endpoint;

import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import pl.codzisnaobiad.imhungry.infrastructure.spoonacular.QuotaPointsCounter;

@Component
@RestControllerEndpoint(id = "spoonacular")
class ImHungryHealthEndpoint {

    private final QuotaPointsCounter quotaPointsCounter;

    ImHungryHealthEndpoint(QuotaPointsCounter quotaPointsCounter) {
        this.quotaPointsCounter = quotaPointsCounter;
    }

    @GetMapping
    public RequestCountResponse getSpoonacularRequestsCount() {
        return new RequestCountResponse(quotaPointsCounter.getQuotaPoints());
    }

    private static class RequestCountResponse {

        private final int requestCount;

        RequestCountResponse(int requestCount) {
            this.requestCount = requestCount;
        }

        public int getRequestCount() {
            return requestCount;
        }

    }

}

