package hu.elte.progtech.spynet.presentation.character.create;

import hu.elte.progtech.spynet.presentation.house.HouseNameListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CreateCharacterDataController {

    public static final String REQUEST_MAPPING = "/createCharacter";

    @Autowired
    private CreateCharacterDataService createCharacterDataService;

    @Autowired
    private String[] status;

    @Autowired
    private HouseNameListService houseNameListService;

    @ModelAttribute("status")
    public String[] getStatus() {
        return status;
    }

    @ModelAttribute("houseNameList")
    public List<String> getHouseNameList() {
        return houseNameListService.getHouseNameList();
    }

    @ModelAttribute
    private CharacterRequest characterRequest(CharacterRequest characterRequest) {
        return characterRequest;
    }

    @RequestMapping(REQUEST_MAPPING)
    public String createCharacter() {
        return "createCharacter";
    }

    @RequestMapping("/postCreateCharacterData.html")
    public String createCharacterFromForm(@Valid @ModelAttribute("characterRequest") CharacterRequest characterRequest,
                                      BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        String saveMessage;
        if (bindingResult.hasErrors()) {
            return "createCharacter";
        } else {
            if (createCharacterDataService.saveCharacter(characterRequest)) {
                saveMessage = String.format("New character: '%s' has been saved!", characterRequest.getName());
            } else {
                saveMessage = String.format("This character: '%s' has been saved already!", characterRequest.getName());
            }
        }
        redirectAttributes.addFlashAttribute("saveMessage", saveMessage);
        return "redirect:home";
    }

}
