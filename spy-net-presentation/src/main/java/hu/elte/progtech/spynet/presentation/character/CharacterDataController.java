package hu.elte.progtech.spynet.presentation.character;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class CharacterDataController {

    @Autowired
    private CharacterDataService characterDataService;

    public static final String REQUEST_MAPPING = "/characters";

    @ModelAttribute("characterDataList")
    public List<CharacterData> characterDataList(ListCharacterRequest listCharacterRequest) {
        if (listCharacterRequest.getName() != null) {
            return characterDataService.filterCharacterDataList(listCharacterRequest.getName());
        } else {
            return characterDataService.getCharacterDataList();
        }
    }

    @RequestMapping(value = {REQUEST_MAPPING}, method = RequestMethod.GET)
    public String characters() {
        return "characters";
    }

}
