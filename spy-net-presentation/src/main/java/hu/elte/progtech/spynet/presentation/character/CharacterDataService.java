package hu.elte.progtech.spynet.presentation.character;

import hu.elte.progtech.spynet.domain.character.Character;
import hu.elte.progtech.spynet.domain.character.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * The class is a singleton class of which responsibility is to handle of the controller's requests.
 */
@Component
public class CharacterDataService {

    @Autowired
    private CharacterService characterService;

    @Autowired
    private CharacterDataTransformer characterDataTransformer;

    /**
     * It asks for existing objects from domain layer. Transformates the elements a list and give it back.
     * @return List<CharacterData>
     */
    public List<CharacterData> getCharacterDataList() {
        return transformToCharacterDataList(characterService.listCharacters());
    }

    private List<CharacterData> transformToCharacterDataList(List<Character> characterList) {
        List<CharacterData> characterDataList = new ArrayList<>();
        for (Character character : characterList) {
            characterDataList.add(characterDataTransformer.transformToCharacterData(character));
        }
        return characterDataList;
    }

    /**
     * It filters on the basis of the input parameter the list of the characters and gives it back.
     * @param filteredName
     * @return List<CharacterData>
     */
    public List<CharacterData> filterCharacterDataList(String filteredName) {
        List<CharacterData> characterDataList = getCharacterDataList();
        List<CharacterData> filteredCharacterDataList = new ArrayList<>();
        for (CharacterData characterData : characterDataList) {
            if (characterData.getName().contains(filteredName)) {
                filteredCharacterDataList.add(characterData);
            }
        }
        return filteredCharacterDataList;
    }
}
