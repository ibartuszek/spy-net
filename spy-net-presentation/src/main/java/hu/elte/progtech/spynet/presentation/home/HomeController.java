package hu.elte.progtech.spynet.presentation.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
/**
 * This class is a singleton and a controller. It makes the view of the main page.
 */
@Controller
public class HomeController {

    public static final String REQUEST_MAPPING = "/home";

    /**
     * Gives back the main page.
     * @return a view
     */
    @RequestMapping(value = {REQUEST_MAPPING, "", "/"})
    public String homepage() {
        return "home";
    }
}
