package pl.codzisnaobiad.imhungry.api.response;


// TODO: 25/08/2020 wszystkie obiekty transportowe powinny byc final
public class FakeSearchRecipeResponse extends SearchRecipeResponse {
    public FakeSearchRecipeResponse() {
        super("123", "Polish kebab", "test");
    }
}
