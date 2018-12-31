package hu.elte.progtech.spynet.presentation.fellowship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
/**
 * This class is a singleton and a controller. It makes contact between view: fellowships and Service.
 */
@Controller
public class FellowshipDataController {

    @Autowired
    private FellowshipDataService fellowshipDataService;

    public static final String REQUEST_MAPPING = "/fellowships";

    @ModelAttribute("fellowshipDataList")
    public List<FellowshipData> fellowshipDataList() {
        return fellowshipDataService.getFellowshipDataList();
    }

    /**
     * Gives back the main page.
     * @return a view
     */
    @RequestMapping(value = {REQUEST_MAPPING})
    public String fellowships() {
        return "fellowships";
    }

}
