package hu.elte.progtech.spynet.presentation.character;

import hu.elte.progtech.spynet.domain.character.Character;
import hu.elte.progtech.spynet.domain.character.CharacterService;
import hu.elte.progtech.spynet.domain.character.CharacterStatus;
import hu.elte.progtech.spynet.presentation.character.modify.ModifyCharacterDataController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class CharacterDataServiceTest {

    @Mock
    private CharacterService characterService;

    @Mock
    private CharacterDataTransformer characterDataTransformer;

    @InjectMocks
    private CharacterDataService underTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCharacterDataListWhenThereIsNoCharacterInDb() {
        // GIVEN
        List<CharacterData> characterDataList = new ArrayList<>();
        List<Character> characterList = new ArrayList<>();
        BDDMockito.given(characterService.listCharacters()).willReturn(characterList);
        // WHEN
        List<CharacterData> result = underTest.getCharacterDataList();
        // THEN
        Assert.assertEquals(characterDataList, result);
    }

    @Test
    public void testGetCharacterDataListWhenThereMoreCharacterInDb() {
        // GIVEN
        List<CharacterData> characterDataList = filledDataWithATwoElementList();
        // WHEN
        List<CharacterData> result = underTest.getCharacterDataList();
        // THEN
        BDDMockito.verify(characterService).listCharacters();
        BDDMockito.verify(characterDataTransformer, BDDMockito.atLeast(2))
                .transformToCharacterData(BDDMockito.isA(Character.class));
        Assert.assertEquals(characterDataList.size(), result.size());
        Assert.assertEquals(characterDataList.get(0), result.get(0));
        Assert.assertEquals(characterDataList.get(1), result.get(1));
    }

    @Test
    public void testFilterCharacterDataListWhenInputIsEmptyString() {
        List<CharacterData> characterDataList = filledDataWithATwoElementList();
        // WHEN
        List<CharacterData> result = underTest.filterCharacterDataList("");
        // THEN
        BDDMockito.verify(characterService).listCharacters();
        BDDMockito.verify(characterDataTransformer, BDDMockito.atLeast(2))
                .transformToCharacterData(BDDMockito.isA(Character.class));
        Assert.assertEquals(characterDataList.size(), result.size());
        Assert.assertEquals(characterDataList.get(0), result.get(0));
        Assert.assertEquals(characterDataList.get(1), result.get(1));
    }

    @Test
    public void testFilterCharacterDataListWhenBothElementsMatches() {
        List<CharacterData> characterDataList = filledDataWithATwoElementList();
        List<CharacterData> filteredList = new ArrayList<>();
        filteredList.add(characterDataList.get(0));
        filteredList.add(characterDataList.get(1));
        // WHEN
        List<CharacterData> result = underTest.filterCharacterDataList("character");
        // THEN
        BDDMockito.verify(characterService).listCharacters();
        BDDMockito.verify(characterDataTransformer, BDDMockito.atLeast(2))
                .transformToCharacterData(BDDMockito.isA(Character.class));
        Assert.assertEquals(filteredList.size(), result.size());
        Assert.assertEquals(filteredList.get(0), result.get(0));
        Assert.assertEquals(filteredList.get(1), result.get(1));
    }

    @Test
    public void testFilterCharacterDataListWhenFirstElementMatches() {
        List<CharacterData> characterDataList = filledDataWithATwoElementList();
        List<CharacterData> filteredList = new ArrayList<>();
        filteredList.add(characterDataList.get(0));
        // WHEN
        List<CharacterData> result = underTest.filterCharacterDataList("character1");
        // THEN
        BDDMockito.verify(characterService).listCharacters();
        Assert.assertEquals(filteredList.size(), result.size());
        Assert.assertEquals(filteredList.get(0), result.get(0));
    }

    @Test
    public void testFilterCharacterDataListWhenNoElementMatches() {
        List<CharacterData> characterDataList = filledDataWithATwoElementList();
        List<CharacterData> filteredList = new ArrayList<>();
        // WHEN
        List<CharacterData> result = underTest.filterCharacterDataList("character12");
        // THEN
        BDDMockito.verify(characterService).listCharacters();
        Assert.assertEquals(filteredList.size(), result.size());
    }

    private List<CharacterData> filledDataWithATwoElementList() {
        List<CharacterData> characterDataList = new ArrayList<>();
        List<Character> characterList = new ArrayList<>();
        fillCharacterList(characterList);
        CharacterData characterData1 = createCharacterDataFromCharacterWithoutHouse(characterList.get(0));
        CharacterData characterData2 = createCharacterDataFromDeadCharacterWithoutHouse(characterList.get(1));
        characterDataList.add(characterData1);
        characterDataList.add(characterData2);
        BDDMockito.given(characterService.listCharacters()).willReturn(characterList);
        BDDMockito.when(characterDataTransformer.transformToCharacterData(BDDMockito.isA(Character.class)))
                .thenReturn(characterDataList.get(0), characterDataList.get(1));
        return characterDataList;
    }

    private void fillCharacterList(List<Character> characterList) {
        Character character1 = Character.createCharacter("character1", 10, null);
        character1.setCharacterId(1);
        Character character2 = Character.createCharacter("character2", 0, null);
        character2.setCharacterId(2);
        character2.setStatus(CharacterStatus.DEAD);
        characterList.add(character1);
        characterList.add(character2);
    }

    private CharacterData createCharacterDataFromCharacterWithoutHouse(Character character) {
        CharacterData characterData = new CharacterData();
        characterData.setCharacterId(character.getCharacterId());
        characterData.setName(character.getName());
        characterData.setCharacterUrl(String.format("%s?characterId=%d",
                ModifyCharacterDataController.REQUEST_MAPPING, characterData.getCharacterId()));
        characterData.setArmySize(character.getArmySize());
        characterData.setHouse("");
        characterData.setStatus("alive");
        return characterData;
    }

    private CharacterData createCharacterDataFromDeadCharacterWithoutHouse(Character character) {
        CharacterData characterData = new CharacterData();
        characterData.setCharacterId(character.getCharacterId());
        characterData.setName(character.getName());
        characterData.setCharacterUrl("");
        characterData.setArmySize(character.getArmySize());
        characterData.setHouse("");
        characterData.setStatus("dead");
        return characterData;
    }




}
