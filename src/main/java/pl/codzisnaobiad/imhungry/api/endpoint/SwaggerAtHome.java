package pl.codzisnaobiad.imhungry.api.endpoint;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/")
public class SwaggerAtHome {

    @ApiIgnore
    @GetMapping("/")
    public String swaggerUi() {
        return "redirect:/swagger-ui.html";
    }

}
