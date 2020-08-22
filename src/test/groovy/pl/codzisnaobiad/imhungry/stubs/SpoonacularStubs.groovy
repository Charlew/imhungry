package pl.codzisnaobiad.imhungry.stubs

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo

trait SpoonacularStubs {
    void stubSpoonacularSearchRecipesByIngredients(List<String> ingredients, String fileName) {
        def ingredientsQuery = String.join(",", ingredients)

        stubFor(get(urlEqualTo("/recipes/findByIngredients?apiKey=API_KEY&ingredients=$ingredientsQuery&number=10&ranking=1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile(fileName)))
    }
}