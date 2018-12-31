package hu.elte.progtech.spynet.presentation.fellowship.modify;

import hu.elte.progtech.spynet.presentation.fellowship.create.FellowshipRequest;
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
 * This class is a singleton and a controller. It makes contact between view: modifyFellowship and Service.
 */
@Controller
public class ModifyFellowshipDataController {

    public static final String REQUEST_MAPPING = "/modifyFellowship";

    @Autowired
    private ModifyFellowshipDataService modifyFellowshipDataService;

    @Autowired
    private HouseNameListService houseNameListService;

    @ModelAttribute("houseNameList")
    public List<String> getHouseNameList() {
        return houseNameListService.getHouseNameList();
    }

    @ModelAttribute
    public FellowshipRequest fellowshipRequest(ModifyFellowshipRequest modifyFellowshipRequest) {
        return modifyFellowshipDataService.createFellowshipRequest(modifyFellowshipRequest);
    }

    /**
     * Gives back the main page.
     * @return a view
     */
    @RequestMapping(REQUEST_MAPPING)
    public String modifyFellowship() {
        return "modifyFellowship";
    }

    /**
     * It transfer the form object to the service than redirect.
     * @return a view of homepage.
     */
    @RequestMapping("/postModifyFellowshipData.html")
    public String modifyFellowshipFromForm(@Valid @ModelAttribute("fellowshipRequest") FellowshipRequest fellowshipRequest,
                                      BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        String saveMessage;
        if (bindingResult.hasErrors()) {
            return "modifyFellowship";
        } else {
            if (modifyFellowshipDataService.saveFellowship(fellowshipRequest)) {
                saveMessage = String.format("New fellowship between: '%s' and '%s' has been modified!",
                        fellowshipRequest.getHouse1(), fellowshipRequest.getHouse2());
            } else {
                saveMessage = String.format("This fellowship between: '%s' and '%s' has not been able to modify!",
                        fellowshipRequest.getHouse1(), fellowshipRequest.getHouse2());
            }
        }
        redirectAttributes.addFlashAttribute("saveMessage", saveMessage);
        return "redirect:home";
    }
}
