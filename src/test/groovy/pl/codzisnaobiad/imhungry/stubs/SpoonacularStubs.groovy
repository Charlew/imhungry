package pl.codzisnaobiad.imhungry.stubs

import org.springframework.util.MultiValueMap
import org.springframework.web.util.UriComponentsBuilder

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo

trait SpoonacularStubs {
    void stubSpoonacularSearchRecipes(MultiValueMap<String, String> queryParameters, String fileName) {
        def url = UriComponentsBuilder.newInstance()
                .pathSegment("recipes", "complexSearch")
                .queryParam("apiKey", "API_KEY")
                .queryParams(queryParameters)
                .queryParam("number", 5)
                .queryParam("instructionsRequired", true)
                .build()
                .toString()

        stubFor(get(urlEqualTo(url))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile(fileName)))
    }

}