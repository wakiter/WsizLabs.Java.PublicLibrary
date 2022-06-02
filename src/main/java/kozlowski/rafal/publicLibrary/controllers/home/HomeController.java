package kozlowski.rafal.publicLibrary.controllers.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public final class HomeController {
    public static final String HomePage = "/";

    @RequestMapping(HomePage)
    public String home() {
        return "home/homepage";
    }
}
