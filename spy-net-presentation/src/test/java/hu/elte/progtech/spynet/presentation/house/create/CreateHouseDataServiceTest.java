package hu.elte.progtech.spynet.presentation.house.create;

import hu.elte.progtech.spynet.domain.house.House;
import hu.elte.progtech.spynet.domain.house.HouseService;
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

import java.util.ArrayList;
import java.util.List;

public class CreateHouseDataServiceTest {

    @Mock
    private HouseService houseService;

    @Mock
    private HouseDataTransformer houseDataTransformer;

    @Mock
    private HouseDataService houseDataService;

    @InjectMocks
    private CreateHouseDataService underTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveHouseWhenInputIsNull() {
        // GIVEN
        HouseRequest houseRequest = null;
        // WHEN
        boolean result = underTest.saveHouse(houseRequest);
        // THEN
        Assert.fail("IllegalArgumentException should be thrown!");
    }

    @Test
    public void testSaveHouseWhenHouseCanBeFoundInDb() {
        // GIVEN
        HouseRequest houseRequest = new HouseRequest();
        houseRequest.setName("house1");
        houseRequest.setSlogan("slogan");
        List<HouseData> houseDataList = createHouseDataList();
        BDDMockito.given(houseDataService.getHouseDataList()).willReturn(houseDataList);
        // WHEN
        boolean result = underTest.saveHouse(houseRequest);
        // THEN
        BDDMockito.verify(houseDataService).getHouseDataList();
        Assert.assertFalse(result);
    }

    @Test
    public void testSaveHouseWhenHouseCannotBeFoundInDb() {
        // GIVEN
        HouseRequest houseRequest = new HouseRequest();
        houseRequest.setName("newHouse");
        houseRequest.setSlogan("newSlogan");
        House house = House.createHouse(houseRequest.getName(), houseRequest.getSlogan());
        List<HouseData> houseDataList = createHouseDataList();
        BDDMockito.given(houseDataService.getHouseDataList()).willReturn(houseDataList);
        BDDMockito.given(houseDataTransformer.transformFromHouseRequest(houseRequest)).willReturn(house);
        BDDMockito.doNothing().when(houseService).saveHouse(house);
        // WHEN
        boolean result = underTest.saveHouse(houseRequest);
        // THEN
        BDDMockito.verify(houseDataService).getHouseDataList();
        BDDMockito.verify(houseDataTransformer).transformFromHouseRequest(houseRequest);
        BDDMockito.verify(houseService).saveHouse(house);
        Assert.assertTrue(result);
    }

    private List<HouseData> createHouseDataList() {
        List<HouseData> houseDataList = new ArrayList<>();
        HouseDataTransformer transformer = new HouseDataTransformer();
        House house1 = House.createHouse("house1", "slogan1");
        House house2 = House.createHouse("house2", "slogan2");
        house1.setHouseId(1);
        house2.setHouseId(1);
        houseDataList.add(transformer.transformToHouseData(house1));
        houseDataList.add(transformer.transformToHouseData(house2));
        return houseDataList;
    }


}
