package hu.elte.progtech.spynet.presentation.character;

import hu.elte.progtech.spynet.domain.character.Character;
import hu.elte.progtech.spynet.domain.character.CharacterStatus;
import hu.elte.progtech.spynet.domain.house.House;
import hu.elte.progtech.spynet.presentation.character.create.CharacterRequest;
import hu.elte.progtech.spynet.presentation.character.modify.ModifyCharacterDataController;
import hu.elte.progtech.spynet.presentation.house.HouseDataService;
import hu.elte.progtech.spynet.presentation.house.HouseDataTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CharacterDataTransformer {

    @Autowired
    private HouseDataService houseDataService;

    @Autowired
    private HouseDataTransformer houseDataTransformer;

    public CharacterData transformToCharacterData(Character character) {
        CharacterData characterData = null;
        if (character != null) {
            characterData = new CharacterData();
            characterData.setCharacterId(character.getCharacterId());
            setCharacterUrl(characterData, character);
            characterData.setName(character.getName());
            characterData.setArmySize(character.getArmySize());
            characterData.setStatus(character.getStatus().getStatus());
            House house = character.getHouse();
            if (house != null) {
                characterData.setHouse(character.getHouse().getName());
            } else {
                characterData.setHouse("");
            }
        }
        return characterData;
    }

    private void setCharacterUrl(CharacterData characterData, Character character) {
        if (character.getStatus().equals(CharacterStatus.ALIVE)) {
            characterData.setCharacterUrl(String.format("%s?characterId=%d",
                    ModifyCharacterDataController.REQUEST_MAPPING, characterData.getCharacterId()));
        }
    }

        public Character transformFromCharacterData(CharacterData characterData) {
            Character character = null;
            if (characterData != null) {
                character = new Character();
                character.setCharacterId(characterData.getCharacterId());
                character.setName(characterData.getName());
                setCharacterStatus(character, characterData.getStatus());
                setCharacterArmySize(character, characterData.getArmySize());
                setCharacterHouse(character, characterData.getHouse());
            }
            return character;
        }

    private void setCharacterStatus(Character character, String status) {
        if ("dead".equals(status)) {
            character.setStatus(CharacterStatus.DEAD);
        } else {
            character.setStatus(CharacterStatus.ALIVE);
        }
    }

    private void setCharacterArmySize(Character character, int armySize) {
        if (character.getStatus().equals(CharacterStatus.DEAD)) {
            character.setArmySize(0);
        } else {
            character.setArmySize(armySize);
        }
    }

    private void setCharacterHouse(Character character, String house) {
        if ("".equals(house)) {
            character.setHouse(null);
        } else {
            character.setHouse(houseDataTransformer
                    .transformFromHouseData(houseDataService.getHouseDataByName(house)));
        }
    }

    public Character transformFromCharacterRequest(CharacterRequest characterRequest) {
        Character character = null;
        if (characterRequest != null) {
            character = new Character();
            character.setName(characterRequest.getName());
            setCharacterStatus(character, characterRequest.getStatus());
            setCharacterArmySize(character, characterRequest.getArmySize());
            setCharacterHouse(character, characterRequest.getHouse());
        }
        return character;
    }

    public CharacterRequest transformToCharacterRequest(Character character) {
        CharacterRequest characterRequest = null;
        if (character != null) {
            characterRequest = new CharacterRequest();
            characterRequest.setName(character.getName());
            characterRequest.setArmySize(character.getArmySize());
            characterRequest.setStatus(character.getStatus().getStatus());
            House house = character.getHouse();
            if (house != null) {
                characterRequest.setHouse(character.getHouse().getName());
            } else {
                characterRequest.setHouse("");
            }
        }
        return characterRequest;
    }
}
