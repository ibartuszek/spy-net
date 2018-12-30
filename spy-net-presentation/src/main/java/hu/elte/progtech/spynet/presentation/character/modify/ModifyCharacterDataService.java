package hu.elte.progtech.spynet.presentation.character.modify;

import hu.elte.progtech.spynet.domain.character.Character;
import hu.elte.progtech.spynet.domain.character.CharacterService;
import hu.elte.progtech.spynet.domain.character.CharacterStatus;
import hu.elte.progtech.spynet.domain.house.House;
import hu.elte.progtech.spynet.domain.house.HouseService;
import hu.elte.progtech.spynet.presentation.character.CharacterData;
import hu.elte.progtech.spynet.presentation.character.CharacterDataService;
import hu.elte.progtech.spynet.presentation.character.CharacterDataTransformer;
import hu.elte.progtech.spynet.presentation.character.create.CharacterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ModifyCharacterDataService {

    @Autowired
    private CharacterService characterService;

    @Autowired
    private HouseService houseService;

    @Autowired
    private CharacterDataService characterDataService;

    @Autowired
    private CharacterDataTransformer characterDataTransformer;

    public CharacterRequest createCharacterRequest(ModifyCharacterRequest modifyCharacterRequest) {
        CharacterRequest characterRequest = new CharacterRequest();
        if (modifyCharacterRequest.getCharacterId() != 0) {
            Character character = characterService.findById(modifyCharacterRequest.getCharacterId());
            characterRequest = characterDataTransformer.transformToCharacterRequest(character);
        }
        return characterRequest;
    }

    public boolean saveCharacter(CharacterRequest characterRequest) {
        boolean modified = false;
        List<CharacterData> characterDataList = characterDataService.getCharacterDataList();
        CharacterData characterData = getCharacterDataFromList(characterDataList, characterRequest.getName());
        if (characterData != null) {
            modified = modifyCharacter(characterRequest, characterData);
        }
        return modified;
    }

    private CharacterData getCharacterDataFromList(List<CharacterData> characterDataList, String name) {
        CharacterData characterData = null;
        boolean found = false;
        for (int i = 0; i < characterDataList.size() && ! found; i++) {
            if(name.equals(characterDataList.get(i).getName())) {
                found = true;
                characterData = characterDataList.get(i);
            }
        }
        return  characterData;
    }

    private boolean modifyCharacter(CharacterRequest characterRequest, CharacterData characterData) {
        boolean modified = true;
        Character character = characterService.findById(characterData.getCharacterId());
        if (CharacterStatus.ALIVE.equals(character.getStatus())) {
            modifyCharacter(character, characterRequest);
            characterService.updateCharacter(character);
        } else {
            modified = false;
        }
        return modified;
    }

    private void modifyCharacter(Character character, CharacterRequest characterRequest) {
        if ("dead".equals(characterRequest.getStatus())) {
            character.setStatus(CharacterStatus.DEAD);
            character.setArmySize(0);
        } else {
            character.setArmySize(characterRequest.getArmySize());
        }
        House house = null;
        if (!"".equals(characterRequest.getHouse())) {
            house = houseService.getHouseByName(characterRequest.getHouse());
        }
        character.setHouse(house);
    }
}
