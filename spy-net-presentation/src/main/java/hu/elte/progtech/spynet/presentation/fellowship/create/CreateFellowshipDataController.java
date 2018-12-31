package hu.elte.progtech.spynet.presentation.fellowship.create;

import hu.elte.progtech.spynet.presentation.house.HouseNameListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * This class is a singleton and a controller. It make contact between view: createFellowship and Service.
 */
@Controller
public class CreateFellowshipDataController {

    public static final String REQUEST_MAPPING = "/createFellowship";

    @Autowired
    private CreateFellowshipDataService createFellowshipDataService;

    @Autowired
    private HouseNameListService houseNameListService;

    @ModelAttribute("houseNameList")
    public List<String> getHouseNameList() {
        return houseNameListService.getHouseNameList();
    }

    @ModelAttribute
    private FellowshipRequest fellowshipRequest(FellowshipRequest fellowshipRequest) {
        return fellowshipRequest;
    }

    /**
     * Gives back the main page.
     * @return a view
     */
    @RequestMapping(REQUEST_MAPPING)
    public String createFellowship() {
        return "createFellowship";
    }

    /**
     * It transfer the form object to the service than redirect.
     * @return a view of homepage.
     */
    @RequestMapping("/postCreateFellowshipData.html")
    public String createHouseFromForm(@Valid @ModelAttribute("fellowshipRequest") FellowshipRequest fellowshipRequest,
                                      BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        String saveMessage;
        if (bindingResult.hasErrors()) {
            return "createFellowship";
        } else {
            if (createFellowshipDataService.checkRequestHouses(fellowshipRequest)) {
                saveMessage = String.format("This fellowship between: '%s' and '%s' is not valid!",
                        fellowshipRequest.getHouse1(), fellowshipRequest.getHouse2());
                redirectAttributes.addFlashAttribute("saveMessage", saveMessage);
                return "redirect:createFellowship";
            }
            if (createFellowshipDataService.saveFellowship(fellowshipRequest)) {
                saveMessage = String.format("New fellowship between: '%s' and '%s' has been saved!",
                        fellowshipRequest.getHouse1(), fellowshipRequest.getHouse2());
            } else {
                saveMessage = String.format("This fellowship between: '%s' and '%s' has not been able to save!",
                        fellowshipRequest.getHouse1(), fellowshipRequest.getHouse2());
            }
        }
        redirectAttributes.addFlashAttribute("saveMessage", saveMessage);
        return "redirect:home";
    }

}
