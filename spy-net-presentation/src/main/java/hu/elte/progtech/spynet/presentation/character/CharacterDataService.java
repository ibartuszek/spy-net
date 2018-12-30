package hu.elte.progtech.spynet.presentation.character;

import hu.elte.progtech.spynet.domain.character.Character;
import hu.elte.progtech.spynet.domain.character.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CharacterDataService {

    @Autowired
    private CharacterService characterService;

    @Autowired
    private CharacterDataTransformer characterDataTransformer;

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
