package hu.elte.progtech.spynet.presentation.house.create;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * This class is a singleton and a controller. It makes contact between view: createHouse and Service.
 */
@Controller
public class CreateHouseDataController {

    public static final String REQUEST_MAPPING = "/createHouse";

    @Autowired
    private CreateHouseDataService createHouseDataService;

    @ModelAttribute
    public HouseRequest houseRequest(HouseRequest houseRequest) {
        return houseRequest;
    }

    /**
     * Gives back the main page.
     * @return a view
     */
    @RequestMapping(REQUEST_MAPPING)
    public String createHouse() {
        return "createHouse";
    }

    /**
     * It transfer the form object to the service than redirect.
     * @return a view of homepage.
     */
    @RequestMapping("/postCreateHouseData.html")
    public String createHouseFromForm(@Valid @ModelAttribute("houseRequest") HouseRequest houseRequest,
                                      BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        String saveMessage;
        if (bindingResult.hasErrors()) {
            return "createHouse";
        } else {
            if (createHouseDataService.saveHouse(houseRequest)) {
                saveMessage = String.format("New house: '%s' has been saved!", houseRequest.getName());
            } else {
                saveMessage = String.format("This house: '%s' has been saved already!", houseRequest.getName());
            }
        }
        redirectAttributes.addFlashAttribute("saveMessage", saveMessage);
        return "redirect:home";
    }
}

