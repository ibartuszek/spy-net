package hu.elte.progtech.spynet.presentation.character;

import hu.elte.progtech.spynet.domain.character.Character;
import hu.elte.progtech.spynet.domain.character.CharacterStatus;
import hu.elte.progtech.spynet.domain.house.House;
import hu.elte.progtech.spynet.presentation.character.create.CharacterRequest;
import hu.elte.progtech.spynet.presentation.character.modify.ModifyCharacterDataController;
import hu.elte.progtech.spynet.presentation.house.HouseData;
import hu.elte.progtech.spynet.presentation.house.HouseDataService;
import hu.elte.progtech.spynet.presentation.house.HouseDataTransformer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CharacterDataTransformerTest {

    @Mock
    private HouseDataService houseDataService;

    @Mock
    private HouseDataTransformer houseDataTransformer;

    @InjectMocks
    private CharacterDataTransformer underTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testTransformToCharacterDataWhenInputIsNull() {
        // GIVEN
        Character character = null;
        CharacterData characterData = null;
        // WHEN
        CharacterData result = underTest.transformToCharacterData(character);
        // THEN
        Assert.assertEquals(characterData, result);
    }

    @Test
    public void testTransformToCharacterDataWhenInputIsNotNullButHouseIsNull() {
        // GIVEN
        Character character = Character.createCharacter("name", 0, null);
        character.setCharacterId(1);
        CharacterData characterData = new CharacterData();
        characterData.setName(character.getName());
        characterData.setArmySize(character.getArmySize());
        characterData.setStatus(character.getStatus().getStatus());
        characterData.setHouse("");
        characterData.setCharacterId(character.getCharacterId());
        characterData.setCharacterUrl(String.format("%s?characterId=%d",
                ModifyCharacterDataController.REQUEST_MAPPING, characterData.getCharacterId()));
        // WHEN
        CharacterData result = underTest.transformToCharacterData(character);
        // THEN
        Assert.assertEquals(characterData, result);
    }

    @Test
    public void testTransformToCharacterDataWhenInputIsNotNullButHouseIsNotNull() {
        // GIVEN
        House house = House.createHouse("house", "slogan");
        Character character = Character.createCharacter("name", 0, house);
        character.setCharacterId(1);
        CharacterData characterData = new CharacterData();
        characterData.setName(character.getName());
        characterData.setArmySize(character.getArmySize());
        characterData.setStatus(character.getStatus().getStatus());
        characterData.setHouse(house.getName());
        characterData.setCharacterId(character.getCharacterId());
        characterData.setCharacterUrl(String.format("%s?characterId=%d",
                ModifyCharacterDataController.REQUEST_MAPPING, characterData.getCharacterId()));
        // WHEN
        CharacterData result = underTest.transformToCharacterData(character);
        // THEN
        Assert.assertEquals(characterData, result);
    }

    @Test
    public void testTransformToCharacterDataWhenCharacterIsDead() {
        // GIVEN
        House house = House.createHouse("house", "slogan");
        Character character = Character.createCharacter("name", 0, house);
        character.setCharacterId(1);
        character.setStatus(CharacterStatus.DEAD);
        CharacterData characterData = new CharacterData();
        characterData.setName(character.getName());
        characterData.setArmySize(character.getArmySize());
        characterData.setStatus(character.getStatus().getStatus());
        characterData.setHouse(house.getName());
        characterData.setCharacterId(character.getCharacterId());
        characterData.setCharacterUrl(null);
        // WHEN
        CharacterData result = underTest.transformToCharacterData(character);
        // THEN
        Assert.assertEquals(characterData, result);
    }

    @Test
    public void testTransformFromCharacterRequestWhenInputIsNull() {
        // GIVEN
        CharacterRequest characterRequest = null;
        Character character = null;
        // WHEN
        Character result = underTest.transformFromCharacterRequest(characterRequest);
        // THEN
        Assert.assertEquals(character, result);
    }

    @Test
    public void testTransformFromCharacterRequestWhenInputIsNotNull() {
        // GIVEN
        CharacterRequest characterRequest = createCharacterRequestWithoutHouse();
        Character character = Character.createCharacter(characterRequest.getName(), characterRequest.getArmySize(), null);
        // WHEN
        Character result = underTest.transformFromCharacterRequest(characterRequest);
        // THEN
        Assert.assertEquals(character, result);
    }

    @Test
    public void testTransformFromCharacterRequestWhenInputAndHouseIsNotNull() {
        // GIVEN
        String houseName = "house";
        House house = House.createHouse(houseName, "slogan");
        HouseData houseData = new HouseData();
        houseData.setName(house.getName());
        houseData.setSlogan(house.getSlogan());
        houseData.setCrestUrl("");
        houseData.setHasCrest(false);
        CharacterRequest characterRequest = createCharacterRequestWithoutHouse();
        characterRequest.setHouse(houseName);
        Character character = Character.createCharacter(characterRequest.getName(), characterRequest.getArmySize(), null);
        character.setHouse(house);
        BDDMockito.given(houseDataService.getHouseDataByName(houseName)).willReturn(houseData);
        BDDMockito.given(houseDataTransformer.transformFromHouseData(houseData)).willReturn(house);
        // WHEN
        Character result = underTest.transformFromCharacterRequest(characterRequest);
        // THEN
        BDDMockito.verify(houseDataService).getHouseDataByName(houseName);
        BDDMockito.verify(houseDataTransformer).transformFromHouseData(houseData);
        Assert.assertEquals(character, result);
    }

    @Test
    public void testTransformFromCharacterRequestWhenCharacterIsDead() {
        // GIVEN
        CharacterRequest characterRequest = createCharacterRequestWithoutHouse();
        Character character = Character.createCharacter(characterRequest.getName(), characterRequest.getArmySize(), null);
        characterRequest.setStatus("dead");
        character.setStatus(CharacterStatus.DEAD);
        character.setArmySize(0);
        // WHEN
        Character result = underTest.transformFromCharacterRequest(characterRequest);
        // THEN
        Assert.assertEquals(character, result);
    }

    @Test
    public void testTransformToCharacterRequestWhenInputIsNull() {
        // GIVEN
        Character character = null;
        CharacterRequest characterRequest = null;
        // WHEN
        CharacterRequest result = underTest.transformToCharacterRequest(character);
        // THEN
        Assert.assertEquals(characterRequest, result);
    }

    @Test
    public void testTransformToCharacterRequestWhenHouseIsNull() {
        // GIVEN
        Character character = Character.createCharacter("name", 10, null);
        CharacterRequest characterRequest = new CharacterRequest();
        characterRequest.setName(character.getName());
        characterRequest.setStatus("alive");
        characterRequest.setArmySize(character.getArmySize());
        characterRequest.setHouse("");
        // WHEN
        CharacterRequest result = underTest.transformToCharacterRequest(character);
        // THEN
        Assert.assertEquals(characterRequest, result);
    }

    @Test
    public void testTransformToCharacterRequestWhenHouseIsNotNull() {
        // GIVEN
        House house = House.createHouse("house", "slogan");
        Character character = Character.createCharacter("name", 10, house);
        CharacterRequest characterRequest = new CharacterRequest();
        characterRequest.setName(character.getName());
        characterRequest.setStatus("alive");
        characterRequest.setArmySize(character.getArmySize());
        characterRequest.setHouse(character.getHouse().getName());
        // WHEN
        CharacterRequest result = underTest.transformToCharacterRequest(character);
        // THEN
        Assert.assertEquals(characterRequest, result);
    }

    @Test
    public void testTransformToCharacterRequestWhenCharacterIsDead() {
        // GIVEN
        Character character = Character.createCharacter("name", 10, null);
        character.setStatus(CharacterStatus.DEAD);
        CharacterRequest characterRequest = new CharacterRequest();
        characterRequest.setName(character.getName());
        characterRequest.setStatus("dead");
        characterRequest.setArmySize(character.getArmySize());
        characterRequest.setHouse("");
        // WHEN
        CharacterRequest result = underTest.transformToCharacterRequest(character);
        // THEN
        Assert.assertEquals(characterRequest, result);
    }

    private CharacterRequest createCharacterRequestWithoutHouse() {
        CharacterRequest characterRequest = new CharacterRequest();
        characterRequest.setName("name");
        characterRequest.setArmySize(10);
        characterRequest.setStatus("alive");
        return characterRequest;
    }
}
