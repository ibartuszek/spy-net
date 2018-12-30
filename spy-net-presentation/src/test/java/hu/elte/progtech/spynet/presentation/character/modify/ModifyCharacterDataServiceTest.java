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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class ModifyCharacterDataServiceTest {

    @Mock
    private CharacterService characterService;

    @Mock
    private HouseService houseService;

    @Mock
    private CharacterDataService characterDataService;

    @Mock
    private CharacterDataTransformer characterDataTransformer;

    @InjectMocks
    private ModifyCharacterDataService underTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateCharacterRequestWhenCharacterIdIsZero() {
        // GIVEN
        ModifyCharacterRequest modifyCharacterRequest = new ModifyCharacterRequest();
        modifyCharacterRequest.setCharacterId(0);
        // WHEN
        CharacterRequest result = underTest.createCharacterRequest(modifyCharacterRequest);
        // THEN
        Assert.assertEquals(0, result.getArmySize());
        Assert.assertNull(result.getName());
        Assert.assertNull(result.getHouse());
        Assert.assertNull(result.getStatus());
    }

    @Test
    public void testCreateCharacterRequestWhenCharacterIdIsNotZero() {
        // GIVEN
        ModifyCharacterRequest modifyCharacterRequest = new ModifyCharacterRequest();
        modifyCharacterRequest.setCharacterId(1);
        Character character = Character.createCharacter("character", 0, null);
        character.setCharacterId(1);
        CharacterRequest characterRequest = createCharacterRequest(character);
        BDDMockito.given(characterService.findById(character.getCharacterId())).willReturn(character);
        BDDMockito.given(characterDataTransformer.transformToCharacterRequest(character)).willReturn(characterRequest);
        // WHEN
        CharacterRequest result = underTest.createCharacterRequest(modifyCharacterRequest);
        // THEN
        Assert.assertEquals(characterRequest, result);
    }

    @Test
    public void testSaveCharacterWhenCharacterCannotBeFound() {
        // GIVEN
        Character character = Character.createCharacter("character", 0, null);
        character.setCharacterId(1);
        CharacterRequest characterRequest = createCharacterRequest(character);
        List<CharacterData> characterDataListList = createCharacterDataList();
        BDDMockito.given(characterDataService.getCharacterDataList()).willReturn(characterDataListList);
        // WHEN
        boolean result = underTest.saveCharacter(characterRequest);
        // THEN
        BDDMockito.verify(characterDataService).getCharacterDataList();
        Assert.assertFalse(result);
    }

    @Test
    public void testSaveCharacterWhenCharacterCanBeFoundAndCanModify() {
        // GIVEN
        Character character = Character.createCharacter("character1", 10, null);
        character.setCharacterId(2);
        House house = House.createHouse("house", "slogan");
        CharacterRequest characterRequest = createCharacterRequest(character);
        characterRequest.setStatus("dead");
        characterRequest.setHouse(house.getName());
        List<CharacterData> characterDataListList = createCharacterDataList();
        BDDMockito.given(characterDataService.getCharacterDataList()).willReturn(characterDataListList);
        BDDMockito.given(characterService.findById(character.getCharacterId())).willReturn(character);
        BDDMockito.given(houseService.getHouseByName(house.getName())).willReturn(house);
        BDDMockito.doNothing().when(characterService).updateCharacter(character);
        // WHEN
        boolean result = underTest.saveCharacter(characterRequest);
        // THEN
        BDDMockito.verify(characterDataService).getCharacterDataList();
        BDDMockito.verify(characterService).findById(character.getCharacterId());
        BDDMockito.verify(houseService).getHouseByName(house.getName());
        BDDMockito.verify(characterService).updateCharacter(character);
        Assert.assertEquals(CharacterStatus.DEAD, character.getStatus());
        Assert.assertEquals(0, character.getArmySize());
        Assert.assertEquals(house, character.getHouse());
        Assert.assertTrue(result);
    }

    @Test
    public void testSaveCharacterWhenCharacterCanBeFoundAndCannotModify() {
        // GIVEN
        Character character = Character.createCharacter("character1", 10, null);
        character.setCharacterId(2);
        character.setStatus(CharacterStatus.DEAD);
        House house = House.createHouse("house", "slogan");
        CharacterRequest characterRequest = createCharacterRequest(character);
        characterRequest.setHouse(house.getName());
        List<CharacterData> characterDataListList = createCharacterDataList();
        BDDMockito.given(characterDataService.getCharacterDataList()).willReturn(characterDataListList);
        BDDMockito.given(characterService.findById(character.getCharacterId())).willReturn(character);
        // WHEN
        boolean result = underTest.saveCharacter(characterRequest);
        // THEN
        BDDMockito.verify(characterDataService).getCharacterDataList();
        BDDMockito.verify(characterService).findById(character.getCharacterId());
        Assert.assertFalse(result);
    }

    private CharacterRequest createCharacterRequest(Character character) {
        return new CharacterDataTransformer().transformToCharacterRequest(character);
    }

    private List<CharacterData> createCharacterDataList() {
        List<CharacterData> characterDataList = new ArrayList<>();
        List<Character> characterList = createCharacterList();
        CharacterDataTransformer transformer = new CharacterDataTransformer();
        for (Character character : characterList) {
            characterDataList.add(transformer.transformToCharacterData(character));
        }
        return characterDataList;
    }

    private List<Character> createCharacterList() {
        List<Character> characterList = new ArrayList<>();
        Character character1 = Character.createCharacter("character1", 10, null);
        Character character2 = Character.createCharacter("character2", 20, null);
        character1.setCharacterId(2);
        character2.setCharacterId(3);
        characterList.add(character1);
        characterList.add(character2);
        return characterList;
    }

}
