package hu.elte.progtech.spynet.dal.house;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class HouseDaoTest {

    @Mock
    private HouseRepository houseRepository;

    @InjectMocks
    private HouseDao underTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testListHousesWhenThereAreNotAnyEntities() {
        // GIVEN
        BDDMockito.given(houseRepository.findAll()).willReturn(Collections.emptyList());
        // WHEN
        List<HouseDto> resultList = underTest.listHouses();
        // THEN
        BDDMockito.verify(houseRepository).findAll();
        Assert.assertEquals(Collections.emptyList(), resultList);
    }

    @Test
    public void testListHousesWhenThereIsOneEntity() {
        // GIVEN
        HouseEntity exampleHouseEntity = HouseEntity.createHouseEntity("house", "slogan");
        exampleHouseEntity.setHouseId(1);
        List<HouseEntity> houseEntityList = new ArrayList<>();
        houseEntityList.add(exampleHouseEntity);
        BDDMockito.given(houseRepository.findAll()).willReturn(houseEntityList);
        List<HouseDto> houseDtoList = new ArrayList<>();
        houseDtoList.add(new HouseDto(exampleHouseEntity));
        // WHEN
        List<HouseDto> resultList = underTest.listHouses();
        // THEN
        BDDMockito.verify(houseRepository).findAll();
        Assert.assertEquals(houseDtoList, resultList);
        Assert.assertEquals(houseDtoList.get(0), resultList.get(0));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testSaveHouseThrowIllegalArgumentExceptionWhenInputIsNull() {
        // GIVEN
        HouseDto houseDto = null;
        // WHEN
        underTest.saveHouse(houseDto);
        // THEN
        Assert.fail("IllegalArgumentException should be thrown!");
    }

    @Test
    public void testSaveHouseWhenInputIsNotNull() {
        // GIVEN
        HouseEntity exampleHouseEntity = HouseEntity.createHouseEntity("house", "slogan");
        exampleHouseEntity.setHouseId(0);
        BDDMockito.given(houseRepository.save(exampleHouseEntity))
                .willReturn(exampleHouseEntity);
        // WHEN
        underTest.saveHouse(new HouseDto(exampleHouseEntity));
        // THEN
        BDDMockito.verify(houseRepository).save(exampleHouseEntity);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testFindByNameWhenInputIsNull() {
        // GIVEN
        // WHEN
        underTest.findByName(null);
        // THEN
        Assert.fail("IllegalArgumentException should be thrown!");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testFindByNameWhenInputIsEmptyString() {
        // GIVEN
        // WHEN
        underTest.findByName("");
        // THEN
        Assert.fail("IllegalArgumentException should be thrown!");
    }

    @Test
    public void testFindByNameWhenHouseCannotFindInDb() {
        // GIVEN
        String houseName = "house";
        HouseDto houseDto = null;
        BDDMockito.given(houseRepository.findByName(houseName))
                .willReturn(Optional.empty());
        // WHEN
        HouseDto result = underTest.findByName(houseName);
        // THEN
        BDDMockito.verify(houseRepository).findByName(houseName);
        Assert.assertEquals(houseDto, result);
    }

    @Test
    public void testFindByNameWhenInputIsValid() {
        // GIVEN
        String houseName = "house";
        HouseEntity houseEntity = HouseEntity.createHouseEntity(houseName, "slogan");
        houseEntity.setHouseId(1);
        HouseDto houseDto = new HouseDto(houseEntity);
        BDDMockito.given(houseRepository.findByName(houseName))
                .willReturn(Optional.of(houseEntity));
        // WHEN
        HouseDto result = underTest.findByName(houseName);
        // THEN
        BDDMockito.verify(houseRepository).findByName(houseName);
        Assert.assertEquals(houseDto, result);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testFindByIdWhenInputIsZero() {
        // GIVEN
        // WHEN
        underTest.findById(0);
        // THEN
        Assert.fail("IllegalArgumentException should be thrown!");
    }

    @Test
    public void testFindByIdWhenHouseCannotFindInDb() {
        // GIVEN
        long id = 1;
        HouseDto houseDto = null;
        BDDMockito.given(houseRepository.findById(id))
                .willReturn(Optional.empty());
        // WHEN
        HouseDto result = underTest.findById(id);
        // THEN
        BDDMockito.verify(houseRepository).findById(id);
        Assert.assertEquals(houseDto, result);
    }

    @Test
    public void testFindByIdWhenInputIsValid() {
        // GIVEN
        long id = 1;
        HouseEntity houseEntity = HouseEntity.createHouseEntity("house", "slogan");
        houseEntity.setHouseId(1);
        HouseDto houseDto = new HouseDto(houseEntity);
        BDDMockito.given(houseRepository.findById(id))
                .willReturn(Optional.of(houseEntity));
        // WHEN
        HouseDto result = underTest.findById(id);
        // THEN
        BDDMockito.verify(houseRepository).findById(id);
        Assert.assertEquals(houseDto, result);
    }

}
