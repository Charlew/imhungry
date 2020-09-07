package pl.codzisnaobiad.imhungry.stubs

import org.springframework.util.MultiValueMap

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import static pl.codzisnaobiad.imhungry.utils.ParamsMapper.queryParamsToSpoonacularUrl
import static pl.codzisnaobiad.imhungry.utils.ParamsMapper.recipeInformationQueryParamsToSpoonacularUrl
import static pl.codzisnaobiad.imhungry.utils.ParamsMapper.recipeInstructionsQueryParamsToSpoonacularUrl

trait SpoonacularStubs {

    void stubSpoonacularSearchRecipes(MultiValueMap<String, String> queryParameters, String fileName) {
        stubFor(get(urlEqualTo("/recipes/complexSearch" + queryParamsToSpoonacularUrl(queryParameters)))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile(fileName)))
    }

    void stubSpoonacularGetRecipeInformationById(String id, String fileName) {
        stubFor(get(urlEqualTo("/recipes/$id/information" + recipeInformationQueryParamsToSpoonacularUrl()))
            .willReturn(aResponse()
                .withStatus(200)
                .withBodyFile(fileName)))
    }

    void stubSpoonacularGetRecipeInstructionsById(String id, String fileName) {
        stubFor(get(urlEqualTo("/recipes/$id/analyzedInstructions" + recipeInstructionsQueryParamsToSpoonacularUrl()))
            .willReturn(aResponse()
                .withStatus(200)
                .withBodyFile(fileName)))
    }
}