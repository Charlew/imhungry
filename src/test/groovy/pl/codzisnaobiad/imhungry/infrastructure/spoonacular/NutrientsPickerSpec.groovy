package pl.codzisnaobiad.imhungry.infrastructure.spoonacular

import pl.codzisnaobiad.imhungry.domain.recipe.SupportedNutrient
import spock.lang.Specification
import spock.lang.Subject

class NutrientsPickerSpec extends Specification {

    @Subject
    NutrientsPicker nutrientsPicker = new NutrientsPicker()

    def "should pick supported nutrients from spoonacular nutrients"() {
        given:
            def spoonacularNutrients = [
                    spoonacularNutrient("Calories", "cal"),
                    spoonacularNutrient("Fat",),
                    spoonacularNutrient("Carbohydrates"),
                    spoonacularNutrient("Protein"),
                    spoonacularNutrient("Sugar"),
                    spoonacularNutrient("Cholesterol", "mg"),
                    spoonacularNutrient("Sodium", "mg"),
                    spoonacularNutrient("Manganese", "mg"),
                    spoonacularNutrient("Vitamin C", "mg"),
                    spoonacularNutrient("Fiber"),
                    spoonacularNutrient("Calcium", "mg"),
                    spoonacularNutrient("Potassium", "mg"),
                    spoonacularNutrient("Saturated Fat"),
            ]

        when:
            def result = nutrientsPicker.pickSupportedNutrients(spoonacularNutrients)

        then:
            result.size() <= EnumSet.allOf(SupportedNutrient.class).size()
    }

    private static SpoonacularRecipeInformationResponse.Nutrition.Nutrient spoonacularNutrient(String title, String unit = "g") {
        new SpoonacularRecipeInformationResponse.Nutrition.Nutrient(title, 100f, unit)
    }

}
