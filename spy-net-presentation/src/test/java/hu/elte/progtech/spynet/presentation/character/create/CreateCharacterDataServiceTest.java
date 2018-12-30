package hu.elte.progtech.spynet.presentation.character.create;

import hu.elte.progtech.spynet.domain.character.Character;
import hu.elte.progtech.spynet.domain.character.CharacterService;
import hu.elte.progtech.spynet.presentation.character.CharacterData;
import hu.elte.progtech.spynet.presentation.character.CharacterDataService;
import hu.elte.progtech.spynet.presentation.character.CharacterDataTransformer;
import hu.elte.progtech.spynet.presentation.character.modify.ModifyCharacterDataController;
import hu.elte.progtech.spynet.presentation.character.modify.ModifyCharacterRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class CreateCharacterDataServiceTest {

    @Mock
    private CharacterService characterService;

    @Mock
    private CharacterDataTransformer characterDataTransformer;

    @Mock
    private CharacterDataService characterDataService;

    @InjectMocks
    private CreateCharacterDataService underTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveCharacterWhenInputIsNull() {
        // GIVEN
        CharacterRequest characterRequest = null;
        // WHEN
        boolean result = underTest.saveCharacter(characterRequest);
        // THEN
        Assert.fail("IllegalArgumentException should be thrown!");
    }

    @Test
    public void testSaveCharacterWhenInputHasAlreadySaved() {
        // GIVEN
        CharacterRequest characterRequest = createCharacterRequest();
        List<CharacterData> characterDataList = new ArrayList<>();
        CharacterData characterData = createSameCharacterData();
        characterDataList.add(characterData);
        BDDMockito.given(characterDataService.getCharacterDataList()).willReturn(characterDataList);
        // WHEN
        boolean result = underTest.saveCharacter(characterRequest);
        // THEN
        BDDMockito.verify(characterDataService).getCharacterDataList();
        Assert.assertFalse(result);
    }

    @Test
    public void testSaveCharacterWhenInputIsNew() {
        // GIVEN
        CharacterRequest characterRequest = createCharacterRequest();
        List<CharacterData> characterDataList = new ArrayList<>();
        CharacterData characterData = createDifferentCharacterData(createSameCharacterData());
        characterDataList.add(characterData);
        Character character = Character.createCharacter(characterRequest.getName(), characterRequest.getArmySize(), null);
        BDDMockito.given(characterDataService.getCharacterDataList()).willReturn(characterDataList);
        BDDMockito.given(characterDataTransformer.transformFromCharacterRequest(characterRequest)).willReturn(character);
        BDDMockito.doNothing().when(characterService).saveCharacter(character);
        // WHEN
        boolean result = underTest.saveCharacter(characterRequest);
        // THEN
        BDDMockito.verify(characterDataService).getCharacterDataList();
        BDDMockito.verify(characterDataTransformer).transformFromCharacterRequest(characterRequest);
        BDDMockito.verify(characterService).saveCharacter(character);
        Assert.assertTrue(result);
    }

    private CharacterRequest createCharacterRequest() {
        CharacterRequest characterRequest = new CharacterRequest();
        characterRequest.setName("name");
        characterRequest.setArmySize(10);
        characterRequest.setStatus("alive");
        characterRequest.setHouse("");
        return characterRequest;
    }

    private CharacterData createSameCharacterData() {
        CharacterData characterData = new CharacterData();
        characterData.setCharacterId(1);
        characterData.setName("name");
        characterData.setStatus("alive");
        characterData.setHouse("");
        characterData.setArmySize(10);
        characterData.setCharacterUrl(String.format("%s?characterId=%d", ModifyCharacterDataController.REQUEST_MAPPING,
                        characterData.getCharacterId()));
        return characterData;
    }

    private CharacterData createDifferentCharacterData(CharacterData characterData) {
        characterData.setCharacterId(2);
        characterData.setName("character");
        characterData.setCharacterUrl(String.format("%s?characterId=%d", ModifyCharacterDataController.REQUEST_MAPPING,
                characterData.getCharacterId()));
        return characterData;
    }

}
