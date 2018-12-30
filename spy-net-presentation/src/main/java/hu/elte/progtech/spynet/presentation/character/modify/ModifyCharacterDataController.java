package hu.elte.progtech.spynet.presentation.character.modify;

import hu.elte.progtech.spynet.presentation.character.create.CharacterRequest;
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
public class ModifyCharacterDataController {

    public static final String REQUEST_MAPPING = "/modifyCharacter";

    @Autowired
    private ModifyCharacterDataService modifyCharacterDataService;

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
    private CharacterRequest characterRequest(ModifyCharacterRequest modifyCharacterRequest) {
        return modifyCharacterDataService.createCharacterRequest(modifyCharacterRequest);
    }

    @RequestMapping(REQUEST_MAPPING)
    public String modifyCharacter() {
        return "modifyCharacter";
    }

    @RequestMapping("/postModifyCharacterData.html")
    public String modifyCharacterFromForm(@Valid @ModelAttribute("characterRequest") CharacterRequest characterRequest,
                                      BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        String saveMessage;
        if (bindingResult.hasErrors()) {
            return "modifyCharacter";
        } else {
            if (modifyCharacterDataService.saveCharacter(characterRequest)) {
                saveMessage = String.format("Character: '%s' has been saved!", characterRequest.getName());
            } else {
                saveMessage = String.format("This character: '%s' has not been able to modify already!", characterRequest.getName());
            }
        }
        redirectAttributes.addFlashAttribute("saveMessage", saveMessage);
        return "redirect:home";
    }

}
