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

    @Test(expected = IllegalArgumentException.class)
    public void testGetHouseByNameWhenInputIsNull() {
        // GIVEN
        // WHEN
        underTest.getHouseByName(null);
        // THEN
        Assert.fail("IllegalArgumentException should be thrown!");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetHouseByNameWhenInputIsEmptyString() {
        // GIVEN
        // WHEN
        underTest.getHouseByName("");
        // THEN
        Assert.fail("IllegalArgumentException should be thrown!");
    }

    @Test
    public void testGetHouseByNameWhenTheHouseCannotFindInDb() {
        // GIVEN
        String houseName = "house";
        House house = null;
        HouseDto houseDto = null; //HouseUtil.createHouseDto(house);
        BDDMockito.given(houseDao.findByName(houseName)).willReturn(houseDto);
        BDDMockito.given(houseTransformer.transformFromHouseDto(houseDto)).willReturn(house);
        // WHEN
        House result = underTest.getHouseByName(houseName);
        // THEN
        BDDMockito.verify(houseDao).findByName(BDDMockito.isA(String.class));
        BDDMockito.verify(houseTransformer).transformFromHouseDto(null);
        Assert.assertEquals(house, result);
    }

    @Test
    public void testGetHouseByNameWhenInputIsValid() {
        // GIVEN
        String houseName = "house";
        House house = House.createHouse(houseName, "slogan");
        HouseDto houseDto = HouseUtil.createHouseDto(house);
        BDDMockito.given(houseDao.findByName(houseName)).willReturn(houseDto);
        BDDMockito.given(houseTransformer.transformFromHouseDto(houseDto)).willReturn(house);
        // WHEN
        House result = underTest.getHouseByName(houseName);
        // THEN
        BDDMockito.verify(houseDao).findByName(BDDMockito.isA(String.class));
        BDDMockito.verify(houseTransformer).transformFromHouseDto(BDDMockito.isA(HouseDto.class));
        Assert.assertEquals(house, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetHouseByIdWhenInputIsZero() {
        // GIVEN
        // WHEN
        underTest.getHouseById(0);
        // THEN
        Assert.fail("IllegalArgumentException should be thrown!");
    }

    @Test
    public void testGetHouseByIdWhenTheHouseCannotFindInDb() {
        // GIVEN
        long id = 1;
        House house = null;
        HouseDto houseDto = null; //HouseUtil.createHouseDto(house);
        BDDMockito.given(houseDao.findById(id)).willReturn(houseDto);
        BDDMockito.given(houseTransformer.transformFromHouseDto(houseDto)).willReturn(house);
        // WHEN
        House result = underTest.getHouseById(id);
        // THEN
        BDDMockito.verify(houseDao).findById(id);
        BDDMockito.verify(houseTransformer).transformFromHouseDto(null);
        Assert.assertEquals(house, result);
    }

    @Test
    public void testGetHouseByIdWhenInputIsValid() {
        // GIVEN
        long id = 1;
        House house = House.createHouse("house", "slogan");
        house.setHouseId(id);
        HouseDto houseDto = HouseUtil.createHouseDto(house);
        BDDMockito.given(houseDao.findById(id)).willReturn(houseDto);
        BDDMockito.given(houseTransformer.transformFromHouseDto(houseDto)).willReturn(house);
        // WHEN
        House result = underTest.getHouseById(id);
        // THEN
        BDDMockito.verify(houseDao).findById(id);
        BDDMockito.verify(houseTransformer).transformFromHouseDto(BDDMockito.isA(HouseDto.class));
        Assert.assertEquals(house, result);
    }

}
