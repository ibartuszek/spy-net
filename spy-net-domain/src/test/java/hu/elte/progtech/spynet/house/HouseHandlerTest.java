package hu.elte.progtech.spynet.house;

import hu.elte.progtech.spynet.dal.house.HouseDao;
import hu.elte.progtech.spynet.dal.house.HouseDto;
import hu.elte.progtech.spynet.domain.house.House;
import hu.elte.progtech.spynet.domain.house.HouseHandler;
import hu.elte.progtech.spynet.domain.house.HouseTransformer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class HouseHandlerTest {

    @Mock
    private HouseDao houseDao;

    @Mock
    private HouseTransformer houseTransformer;

    @InjectMocks
    private HouseHandler underTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveHouseWhenInputIsNull() {
        // GIVEN
        // WHEN
        underTest.saveHouse(null);
        // THEN
        Assert.fail("IllegalArgumentException should be thrown!");
    }

    @Test
    public void testSaveHouseWhenInputIsValid() {
        // GIVEN
        House house = House.createHouse("house", "slogan");
        HouseDto houseDto = HouseUtil.createHouseDto(house);
        BDDMockito.given(houseTransformer.transformToHouseDto(house)).willReturn(houseDto);
        BDDMockito.doNothing().when(houseDao).saveHouse(houseDto);
        // WHEN
        underTest.saveHouse(house);
        // THEN
        BDDMockito.verify(houseTransformer).transformToHouseDto(house);
        BDDMockito.verify(houseDao).saveHouse(BDDMockito.isA(HouseDto.class));
    }

    @Test
    public void testListHousesWhenThereIsNoElementInDb() {
        // GIVEN
        List<House> houseList = new ArrayList<>();
        BDDMockito.given(houseDao.listHouses()).willReturn(new ArrayList<>());
        // WHEN
        List<House> resultList = underTest.listHouses();
        // THEN
        Assert.assertEquals(houseList, resultList);
    }

    @Test
    public void testListHousesWhenThereAreMoreElementsInDb() {
        // GIVEN
        List<House> houseList = new ArrayList<>();
        House house1 = House.createHouse("house1", "slogan1");
        House house2 = House.createHouse("house2", "slogan2");
        houseList.add(house1);
        houseList.add(house2);
        List<HouseDto> houseDtoList = new ArrayList<>();
        HouseDto houseDto1 = HouseUtil.createHouseDto(house1);
        HouseDto houseDto2 = HouseUtil.createHouseDto(house2);
        houseDtoList.add(houseDto1);
        houseDtoList.add(houseDto2);
        BDDMockito.given(houseDao.listHouses()).willReturn(houseDtoList);
        BDDMockito.when(houseTransformer.transformFromHouseDto(BDDMockito.isA(HouseDto.class)))
                .thenReturn(house1, house2);
        // WHEN
        List<House> resultList = underTest.listHouses();
        // THEN
        BDDMockito.verify(houseDao).listHouses();
        BDDMockito.verify(houseTransformer, BDDMockito.atLeast(2))
                .transformFromHouseDto(BDDMockito.isA(HouseDto.class));
        Assert.assertEquals(houseList, resultList);
    }

}
