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
        Assert.assertEquals(resultList, Collections.emptyList());
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
        Assert.assertEquals(resultList, houseDtoList);
        Assert.assertEquals(resultList.get(0), houseDtoList.get(0));
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


}
