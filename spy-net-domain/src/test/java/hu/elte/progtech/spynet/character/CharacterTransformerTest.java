package hu.elte.progtech.spynet.character;

import hu.elte.progtech.spynet.dal.character.CharacterDto;
import hu.elte.progtech.spynet.dal.house.HouseDto;
import hu.elte.progtech.spynet.domain.character.Character;
import hu.elte.progtech.spynet.domain.character.CharacterStatus;
import hu.elte.progtech.spynet.domain.character.CharacterTransformer;
import hu.elte.progtech.spynet.domain.house.House;
import hu.elte.progtech.spynet.domain.house.HouseTransformer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CharacterTransformerTest {

    @Mock
    private HouseTransformer houseTransformer;

    @InjectMocks
    private CharacterTransformer underTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = NullPointerException.class)
    public void testTransformToCharacterDtoWhenInputIsNull() {
        // GIVEN
        Character character = null;
        // WHEN
        underTest.transformToCharacterDto(character);
        // THEN
        Assert.fail("NullPointerException should be thrown!");
    }

    @Test
    public void testTransformToCharacterDtoWhenInputIsValid() {
        // GIVEN
        Character character = Character.createCharacter("name", 0, null);
        character.setCharacterId(1);
        CharacterDto characterDto = new CharacterDto();
        characterDto.setName(character.getName());
        characterDto.setArmySize(character.getArmySize());
        characterDto.setCharacterId(character.getCharacterId());
        // WHEN
        CharacterDto result = underTest.transformToCharacterDto(character);
        // THEN
        Assert.assertEquals(characterDto, result);
    }

    @Test(expected = NullPointerException.class)
    public void testTransformFromCharacterDtoWhenInputIsNull() {
        // GIVEN
        CharacterDto characterDto = null;
        // WHEN
        underTest.transformFromCharacterDto(characterDto);
        // THEN
        Assert.fail("NullPointerException should be thrown!");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransformFromCharacterDtoWhenStatusCannotBeConverted() {
        // GIVEN
        House house = House.createHouse("house", "slogan");
        house.setHouseId(2);
        HouseDto houseDto = new HouseDto();
        houseDto.setName(house.getName());
        houseDto.setHouseId(house.getHouseId());
        houseDto.setSlogan(house.getSlogan());
        CharacterDto characterDto = new CharacterDto();
        characterDto.setName("name");
        characterDto.setArmySize(0);
        characterDto.setStatus("alive1");
        characterDto.setCharacterId(1);
        characterDto.setHouse(houseDto);
        // WHEN
        underTest.transformFromCharacterDto(characterDto);
        // THEN
        Assert.fail("IllegalArgumentException should be thrown!");
    }

    @Test
    public void testTransformFromCharacterDtoWhenInputIsValid() {
        // GIVEN
        House house = House.createHouse("house", "slogan");
        house.setHouseId(2);
        HouseDto houseDto = new HouseDto();
        houseDto.setName(house.getName());
        houseDto.setHouseId(house.getHouseId());
        houseDto.setSlogan(house.getSlogan());
        CharacterDto characterDto = new CharacterDto();
        characterDto.setName("name");
        characterDto.setArmySize(0);
        characterDto.setStatus("alive");
        characterDto.setCharacterId(1);
        characterDto.setHouse(houseDto);
        Character character = new Character();
        character.setName(characterDto.getName());
        character.setArmySize(character.getArmySize());
        character.setStatus(CharacterStatus.ALIVE);
        character.setCharacterId(characterDto.getCharacterId());
        character.setHouse(house);
        BDDMockito.given(houseTransformer.transformToHouseDto(house)).willReturn(houseDto);
        // WHEN
        Character result = underTest.transformFromCharacterDto(characterDto);
        // THEN
        Assert.assertEquals(character, result);
    }

}
