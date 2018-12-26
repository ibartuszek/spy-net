package hu.elte.progtech.spynet.dal.character;

import hu.elte.progtech.spynet.dal.house.HouseEntity;
import hu.elte.progtech.spynet.dal.house.HouseRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CharacterDaoTest {

    @Mock
    private CharacterRepository characterRepository;

    @Mock
    private HouseRepository houseRepository;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private CharacterDao underTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testListCharactersWhenThereAreNotAnyEntities() {
        // GIVEN
        BDDMockito.given(characterRepository.findAll()).willReturn(Collections.emptyList());
        // WHEN
        List<CharacterDto> resultList = underTest.listCharacters();
        // THEN
        BDDMockito.verify(characterRepository).findAll();
        Assert.assertEquals(Collections.emptyList(), resultList);
    }

    @Test
    public void testListCharactersWhenThereIsOneEntity() {
        // GIVEN
        HouseEntity exampleHouseEntity = HouseEntity.createHouseEntity("house", "slogan");
        exampleHouseEntity.setHouseId(1);
        CharacterEntity exampleCharacterEntity = CharacterEntity.createCharacterEntity("character", exampleHouseEntity);
        exampleCharacterEntity.setCharacterId(2);
        List<CharacterEntity> characterEntityList = new ArrayList<>();
        characterEntityList.add(exampleCharacterEntity);
        BDDMockito.given(characterRepository.findAll()).willReturn(characterEntityList);
        List<CharacterDto> characterDtoList = new ArrayList<>();
        characterDtoList.add(new CharacterDto(exampleCharacterEntity));
        // WHEN
        List<CharacterDto> resultList = underTest.listCharacters();
        // THEN
        BDDMockito.verify(characterRepository).findAll();
        Assert.assertEquals(characterDtoList, resultList);
        Assert.assertEquals(characterDtoList.get(0), resultList.get(0));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testSaveCharacterThrowIllegalArgumentExceptionWhenInputIsNull() {
        // GIVEN
        CharacterDto characterDto = null;
        // WHEN
        underTest.saveCharacter(characterDto);
        // THEN
        Assert.fail("IllegalArgumentException should be thrown!");
    }

    @Test
    public void testSaveCharacterWhenInputIsNotNull() {
        // GIVEN
        HouseEntity exampleHouseEntity = HouseEntity.createHouseEntity("house", "slogan");
        exampleHouseEntity.setHouseId(1);
        CharacterEntity exampleCharacterEntity = CharacterEntity.createCharacterEntity("character", exampleHouseEntity);
        exampleCharacterEntity.setCharacterId(0);
        CharacterDto characterDto = new CharacterDto(exampleCharacterEntity);
        BDDMockito.given(characterRepository.save(exampleCharacterEntity))
                .willReturn(exampleCharacterEntity);
        BDDMockito.given(houseRepository.findById(exampleHouseEntity.getHouseId()))
                .willReturn(Optional.of(exampleHouseEntity));
        // WHEN
        underTest.saveCharacter(characterDto);
        // THEN
        BDDMockito.verify(characterRepository).save(exampleCharacterEntity);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testUpdateCharacterWhenInputIsNull() {
        // GIVEN
        CharacterDto characterDto = null;
        // WHEN
        underTest.updateCharacter(characterDto);
        // THEN
        Assert.fail("IllegalArgumentException should be thrown!");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testUpdateCharacterWhenCharacterCannotBeFoundInDb() {
        // GIVEN
        HouseEntity exampleHouseEntity = HouseEntity.createHouseEntity("house", "slogan");
        exampleHouseEntity.setHouseId(1);
        CharacterEntity exampleCharacterEntity = CharacterEntity.createCharacterEntity("character", exampleHouseEntity);
        exampleCharacterEntity.setCharacterId(2);
        CharacterDto characterDto = new CharacterDto(exampleCharacterEntity);
        characterDto.setStatus("dead");
        exampleCharacterEntity.setStatus("dead");
        characterDto.setArmySize(0);
        exampleCharacterEntity.setArmySize(0);
        BDDMockito.given(houseRepository.findById(exampleHouseEntity.getHouseId()))
                .willReturn(Optional.of(exampleHouseEntity));
        BDDMockito.given(characterRepository.findById(characterDto.getCharacterId()))
                .willReturn(Optional.empty());
        BDDMockito.doNothing().when(entityManager)
                .detach(BDDMockito.isA(CharacterEntity.class));
        BDDMockito.given(characterRepository.save(exampleCharacterEntity))
                .willReturn(exampleCharacterEntity);
        // WHEN
        underTest.updateCharacter(characterDto);
        // THEN
        Assert.fail("IllegalArgumentException should be thrown!");
    }

    @Test
    public void testUpdateCharacterWhenInputIsNotNull() {
        // GIVEN
        HouseEntity exampleHouseEntity = HouseEntity.createHouseEntity("house", "slogan");
        exampleHouseEntity.setHouseId(1);
        CharacterEntity exampleCharacterEntity = CharacterEntity.createCharacterEntity("character", exampleHouseEntity);
        exampleCharacterEntity.setCharacterId(2);
        CharacterDto characterDto = new CharacterDto(exampleCharacterEntity);
        characterDto.setStatus("dead");
        exampleCharacterEntity.setStatus("dead");
        characterDto.setArmySize(0);
        exampleCharacterEntity.setArmySize(0);
        BDDMockito.given(houseRepository.findById(exampleHouseEntity.getHouseId()))
                .willReturn(Optional.of(exampleHouseEntity));
        BDDMockito.given(characterRepository.findById(characterDto.getCharacterId()))
                .willReturn(Optional.of(exampleCharacterEntity));
        BDDMockito.doNothing().when(entityManager)
                .detach(BDDMockito.isA(CharacterEntity.class));
        BDDMockito.given(characterRepository.save(exampleCharacterEntity))
                .willReturn(exampleCharacterEntity);
        // WHEN
        underTest.updateCharacter(characterDto);
        // THEN
        BDDMockito.verify(houseRepository).findById(exampleHouseEntity.getHouseId());
        BDDMockito.verify(characterRepository).findById(characterDto.getCharacterId());
        BDDMockito.verify(entityManager).detach(exampleCharacterEntity);
        BDDMockito.verify(characterRepository).save(exampleCharacterEntity);
    }

}
