package pl.codzisnaobiad.imhungry.infrastructure.spoonacular

import pl.codzisnaobiad.imhungry.api.response.recipe.Equipment
import pl.codzisnaobiad.imhungry.api.response.recipe.Ingredient
import pl.codzisnaobiad.imhungry.api.response.recipe.Length
import pl.codzisnaobiad.imhungry.api.response.recipe.Nutrient
import pl.codzisnaobiad.imhungry.api.response.recipe.RecipeInstructionResponse
import pl.codzisnaobiad.imhungry.api.response.recipe.SearchRecipeResponse
import pl.codzisnaobiad.imhungry.api.response.recipe.Step
import pl.codzisnaobiad.imhungry.api.response.recipe.Temperature
import spock.lang.Specification
import spock.lang.Subject

class SpoonacularMapperSpec extends Specification {

    @Subject
    SpoonacularMapper spoonacularMapper
    NutrientsPicker nutrientsPicker
    UrlGenerator urlGenerator

    def setup() {
        nutrientsPicker = new NutrientsPicker()
        urlGenerator = new UrlGenerator()
        spoonacularMapper = new SpoonacularMapper(nutrientsPicker, urlGenerator)
    }

    def "should prepare search recipes response"() {
        given:
            def spoonacularSearchRecipesResponse = new SpoonacularSearchRecipesResponse(
                    [
                            spoonacularSearchRecipe("123", "spaghetti", "http://spaghetti.jpg"),
                            spoonacularSearchRecipe("456", "kotlets", "http://kotlets.jpg"),
                            spoonacularSearchRecipe("789", "schabowes", "http://schabowes.jpg"),
                    ]
            )
        when:
            def result = spoonacularMapper.prepareSearchRecipesResponse(spoonacularSearchRecipesResponse)

        then:
            result.getRecipes() == [
                    new SearchRecipeResponse("123", "spaghetti", "http://spaghetti.jpg"),
                    new SearchRecipeResponse("456", "kotlets", "http://kotlets.jpg"),
                    new SearchRecipeResponse("789", "schabowes", "http://schabowes.jpg"),
            ]
    }

    def "should prepare recipe ingredients response"() {
        given:
            def spoonacularRecipeInformationResponse = new SpoonacularRecipeInformationResponse(
                    [
                            spoonacularIngredient("1", "pasta", 200f, "g", "pasta.jpg"),
                            spoonacularIngredient("2", "tomatoes", 150f, "g", "tomatoes.jpg"),
                            spoonacularIngredient("3", "pork", 300f, "g", "pork.jpg"),
                    ],
                    "spaghetti",
                    40,
                    2,
                    new SpoonacularRecipeInformationResponse.Nutrition(
                            [
                                    spoonacularNutrient("Calories", 300f),
                                    spoonacularNutrient("Fat", 30f),
                                    spoonacularNutrient("Protein", 50f),
                                    spoonacularNutrient("Carbohydrates", 200f),
                                    spoonacularNutrient("Sugar", 20f)
                            ]
                    )
            )

        when:
            def result = spoonacularMapper.prepareRecipeIngredientsResponse(spoonacularRecipeInformationResponse)

        then:
            result.getServings() == 2
            result.getReadyInMinutes() == 40
            result.getIngredients() == [
                    ingredient("1", "pasta", 200f, "g", "https://spoonacular.com/cdn/ingredients_250x250/pasta.jpg"),
                    ingredient("2", "tomatoes", 150f, "g", "https://spoonacular.com/cdn/ingredients_250x250/tomatoes.jpg"),
                    ingredient("3", "pork", 300f, "g", "https://spoonacular.com/cdn/ingredients_250x250/pork.jpg")
            ]
            result.getNutrients() == [
                    nutrient("Calories", 300f, "g"),
                    nutrient("Fat", 30f, "g"),
                    nutrient("Protein", 50f, "g"),
                    nutrient("Carbohydrates", 200f, "g")
            ]
    }

    def "should prepare recipe instructions response"() {
        given:
            SpoonacularAnalyzedInstructionsResponse[] spoonacularAnalyzedInstructionsResponse = [
                    new SpoonacularAnalyzedInstructionsResponse(
                            "cooking pasta",
                            [
                                    new SpoonacularAnalyzedInstructionsResponse.Step(
                                            1,
                                            "cook pasta",
                                            [new SpoonacularAnalyzedInstructionsResponse.Step.Equipment("pan", "pan.jpg",
                                            new SpoonacularAnalyzedInstructionsResponse.Step.Equipment.Temperature("Celcius", 100f))],
                                            new SpoonacularAnalyzedInstructionsResponse.Step.Length(15, "minutes"))
                            ]
                    ),
                    new SpoonacularAnalyzedInstructionsResponse(
                            "making sauce",
                            [
                                    new SpoonacularAnalyzedInstructionsResponse.Step(
                                            2,
                                            "make sauce!",
                                            [new SpoonacularAnalyzedInstructionsResponse.Step.Equipment("pan", "pan.jpg",
                                                    new SpoonacularAnalyzedInstructionsResponse.Step.Equipment.Temperature("Celcius", 80f))],
                                            new SpoonacularAnalyzedInstructionsResponse.Step.Length(5, "minutes"))
                            ]
                    )
            ] as SpoonacularAnalyzedInstructionsResponse[]

        when:
            def result = spoonacularMapper.prepareRecipeInstructionsResponse(spoonacularAnalyzedInstructionsResponse)

        then:
            result.instructions == [
                    new RecipeInstructionResponse(
                            "cooking pasta",
                            [
                                    Step.newBuilder()
                                            .withNumber(1)
                                            .withStep("cook pasta")
                                            .withEquipment([new Equipment("pan", "https://spoonacular.com/cdn/equipment_250x250/pan.jpg", new Temperature(100f, "Celcius"))])
                                            .withLength(new Length(15, "minutes"))
                                            .build()
                            ]
                    ),
                    new RecipeInstructionResponse(
                            "making sauce",
                            [
                                    Step.newBuilder()
                                            .withNumber(2)
                                            .withStep("make sauce!")
                                            .withEquipment([new Equipment("pan", "https://spoonacular.com/cdn/equipment_250x250/pan.jpg", new Temperature(80f, "Celcius"))])
                                            .withLength(new Length(5, "minutes"))
                                            .build()
                            ]
                    )
            ]

    }

    private static SpoonacularSearchRecipesResponse.SpoonacularSearchRecipeResponse spoonacularSearchRecipe(String id, String title, String imageUrl) {
        new SpoonacularSearchRecipesResponse.SpoonacularSearchRecipeResponse(id, title, imageUrl)
    }

    private static Ingredient ingredient(String id, String name, float amount, String unit, String imageUrl) {
        return Ingredient.newBuilder()
                .withId(id)
                .withName(name)
                .withAmount(amount)
                .withUnit(unit)
                .withImage(imageUrl)
                .build()
    }

    private static Nutrient nutrient(String name, float amount, String unit) {
        return Nutrient.newBuilder()
                .withName(name)
                .withAmount(amount)
                .withUnit(unit)
                .build()
    }

    private static SpoonacularRecipeInformationResponse.Nutrition.Nutrient spoonacularNutrient(String title, float amount) {
        new SpoonacularRecipeInformationResponse.Nutrition.Nutrient(title, amount, "g")
    }

    private static SpoonacularRecipeInformationResponse.ExtendedIngredient spoonacularIngredient(String id, String name, float amount, String unit, String image) {
        new SpoonacularRecipeInformationResponse.ExtendedIngredient(id, name, amount, unit, image)
    }

}
