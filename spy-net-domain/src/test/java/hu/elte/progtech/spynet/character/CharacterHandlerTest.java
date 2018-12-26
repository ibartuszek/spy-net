package hu.elte.progtech.spynet.character;

import hu.elte.progtech.spynet.dal.character.CharacterDao;
import hu.elte.progtech.spynet.dal.character.CharacterDto;
import hu.elte.progtech.spynet.domain.character.Character;
import hu.elte.progtech.spynet.domain.character.CharacterHandler;
import hu.elte.progtech.spynet.domain.character.CharacterStatus;
import hu.elte.progtech.spynet.domain.character.CharacterTransformer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class CharacterHandlerTest {

    @Mock
    private CharacterDao characterDao;

    @Mock
    private CharacterTransformer characterTransformer;

    @InjectMocks
    private CharacterHandler underTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testListCharactersWhenThereIsNoElementInDb(){
        // GIVEN
        List<Character> characterList = new ArrayList<>();
        BDDMockito.given(characterDao.listCharacters()).willReturn(new ArrayList<>());
        // WHEN
        List<Character> resultList = underTest.listCharacters();
        // THEN
        Assert.assertEquals(characterList, resultList);
    }

    @Test
    public void testListCharactersWhenThereAreMoreElementsInDb(){
        // GIVEN
        List<Character> characterList = createCharacterList();
        // WHEN
        List<Character> resultList = underTest.listCharacters();
        // THEN
        BDDMockito.verify(characterDao).listCharacters();
        BDDMockito.verify(characterTransformer, BDDMockito.atLeast(2)).transformFromCharacterDto(BDDMockito.isA(CharacterDto.class));
        Assert.assertEquals(characterList, resultList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testListCharactersByNameWhenInputIsNull() {
        // GIVEN
        // WHEN
        List<Character> resultList = underTest.listCharactersByName(null);
        // THEN
        Assert.fail("IllegalArgumentException should be thrown!");
    }

    @Test
    public void testListCharactersByNameWhenThereIsNoChosenCharacter(){
        // GIVEN
        List<Character> characterList = createCharacterList();
        List<Character> filteredCharacterList = new ArrayList<>();
        // WHEN
        List<Character> resultList = underTest.listCharactersByName("Drogo");
        // THEN
        BDDMockito.verify(characterDao).listCharacters();
        BDDMockito.verify(characterTransformer, BDDMockito.atLeast(2)).transformFromCharacterDto(BDDMockito.isA(CharacterDto.class));
        Assert.assertEquals(0, resultList.size());
        Assert.assertEquals(filteredCharacterList, resultList);
    }

    @Test
    public void testListCharactersByNameWhenThereAreTwoChosenCharacter(){
        // GIVEN
        List<Character> characterList = createCharacterList();
        // WHEN
        List<Character> resultList = underTest.listCharactersByName("ame");
        // THEN
        BDDMockito.verify(characterDao).listCharacters();
        BDDMockito.verify(characterTransformer, BDDMockito.atLeast(2)).transformFromCharacterDto(BDDMockito.isA(CharacterDto.class));
        Assert.assertEquals(2, resultList.size());
        Assert.assertEquals(resultList, characterList);
    }

    @Test
    public void testListCharactersByNameWhenThereIsOnlyOneMatch(){
        // GIVEN
        List<Character> characterList = createCharacterList();
        List<Character> filteredCharacterList = new ArrayList<>();
        filteredCharacterList.add(characterList.get(0));
        // WHEN
        List<Character> resultList = underTest.listCharactersByName("ame1");
        // THEN
        BDDMockito.verify(characterDao).listCharacters();
        BDDMockito.verify(characterTransformer, BDDMockito.atLeast(2))
                .transformFromCharacterDto(BDDMockito.isA(CharacterDto.class));
        Assert.assertEquals(1, resultList.size());
        Assert.assertEquals(filteredCharacterList, resultList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveCharacterWhenInputIsNull() {
        // GIVEN
        // WHEN
        underTest.saveCharacter(null);
        // THEN
        Assert.fail("IllegalArgumentException should be thrown!");
    }

    @Test
    public void testSaveCharacterWhenInputIsValid() {
        // GIVEN
        Character character = Character.createCharacter("name", 0, null);
        character.setCharacterId(1);
        CharacterDto characterDto = createCharacterDtoWithoutHouse(character);
        BDDMockito.given(characterTransformer.transformToCharacterDto(character))
                .willReturn(characterDto);
        BDDMockito.doNothing().when(characterDao).saveCharacter(characterDto);
        // WHEN
        underTest.saveCharacter(character);
        // THEN
        BDDMockito.verify(characterTransformer).transformToCharacterDto(character);
        BDDMockito.verify(characterDao).saveCharacter(BDDMockito.isA(CharacterDto.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateCharacterWhenInputIsNull() {
        // GIVEN
        Character character = null;
        BDDMockito.given(characterTransformer.transformToCharacterDto(character))
                .willThrow(new NullPointerException());
        // WHEN
        underTest.updateCharacter(character);
        // THEN
        Assert.fail("IllegalArgumentException should be thrown!");
    }

    @Test
    public void testUpdateCharacterWhenInputIsValid() {
        // GIVEN
        Character character = Character.createCharacter("name", 0, null);
        character.setCharacterId(1);
        CharacterDto characterDto = createCharacterDtoWithoutHouse(character);
        BDDMockito.given(characterTransformer.transformToCharacterDto(character))
                .willReturn(characterDto);
        BDDMockito.doNothing().when(characterDao).updateCharacter(characterDto);
        // WHEN
        underTest.updateCharacter(character);
        // THEN
        BDDMockito.verify(characterTransformer).transformToCharacterDto(character);
        BDDMockito.verify(characterDao).updateCharacter(BDDMockito.isA(CharacterDto.class));
    }

    @Test
    public void testUpdateCharacterWhenInputIsValidAndCharacterIsDead() {
        // GIVEN
        Character character = Character.createCharacter("name", 100, null);
        character.setCharacterId(1);
        character.setStatus(CharacterStatus.DEAD);
        CharacterDto characterDto = createCharacterDtoWithoutHouse(character);
        BDDMockito.given(characterTransformer.transformToCharacterDto(character))
                .willReturn(characterDto);
        BDDMockito.doNothing().when(characterDao).updateCharacter(characterDto);
        // WHEN
        underTest.updateCharacter(character);
        // THEN
        BDDMockito.verify(characterTransformer).transformToCharacterDto(character);
        BDDMockito.verify(characterDao).updateCharacter(BDDMockito.isA(CharacterDto.class));
        Assert.assertEquals(CharacterStatus.DEAD, character.getStatus());
        Assert.assertEquals(0, character.getArmySize());
    }

    private CharacterDto createCharacterDtoWithoutHouse(Character character) {
        CharacterDto characterDto = new CharacterDto();
        characterDto.setName(character.getName());
        characterDto.setArmySize(character.getArmySize());
        characterDto.setCharacterId(character.getCharacterId());
        if (character.getStatus().equals(CharacterStatus.ALIVE)) {
            characterDto.setStatus("alive");
        } else {
            characterDto.setStatus("dead");
        }
        return characterDto;
    }

    private List<Character> createCharacterList() {
        List<Character> characterList = new ArrayList<>();
        Character character1 = Character.createCharacter("name1", 0, null);
        Character character2 = Character.createCharacter("name2", 0, null);
        characterList.add(character1);
        characterList.add(character2);

        List<CharacterDto> characterDtoList = new ArrayList<>();
        CharacterDto characterDto1 = createCharacterDtoWithoutHouse(character1);
        CharacterDto characterDto2 = createCharacterDtoWithoutHouse(character2);
        characterDtoList.add(characterDto1);
        characterDtoList.add(characterDto2);
        BDDMockito.given(characterDao.listCharacters()).willReturn(characterDtoList);
        BDDMockito.when(characterTransformer.transformFromCharacterDto(BDDMockito.isA(CharacterDto.class)))
                .thenReturn(character1, character2);
        return characterList;
    }

}
