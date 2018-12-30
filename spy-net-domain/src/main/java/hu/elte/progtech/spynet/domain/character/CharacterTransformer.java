package hu.elte.progtech.spynet.domain.character;

import hu.elte.progtech.spynet.dal.character.CharacterDto;
import hu.elte.progtech.spynet.dal.house.HouseDto;
import hu.elte.progtech.spynet.domain.house.House;
import hu.elte.progtech.spynet.domain.house.HouseTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CharacterTransformer {

    @Autowired
    private HouseTransformer houseTransformer;

    public CharacterDto transformToCharacterDto(Character character) {
        CharacterDto characterDto = new CharacterDto();
        HouseDto houseDto = houseTransformer.transformToHouseDto(character.getHouse());
        characterDto.setCharacterId(character.getCharacterId());
        characterDto.setName(character.getName());
        characterDto.setArmySize(character.getArmySize());
        characterDto.setStatus(character.getStatus().getStatus());
        characterDto.setHouse(houseDto);
        return characterDto;
    }

    public Character transformFromCharacterDto(CharacterDto characterDto) {
        Character character = new Character();
        House house = null;
        HouseDto houseDto = characterDto.getHouse();
        if (houseDto != null) {
            house = houseTransformer.transformFromHouseDto(houseDto);
        }
        character.setCharacterId(characterDto.getCharacterId());
        character.setName(characterDto.getName());
        character.setArmySize(characterDto.getArmySize());
        character.setStatus(CharacterStatus.getCharacterStatus(characterDto.getStatus()));
        character.setHouse(house);
        return character;
    }
}
