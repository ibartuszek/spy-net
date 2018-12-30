package hu.elte.progtech.spynet.presentation.house;

import hu.elte.progtech.spynet.domain.house.House;
import hu.elte.progtech.spynet.domain.house.HouseService;
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

public class HouseDataServiceTest {

    @Mock
    private HouseService houseService;

    @Mock
    private HouseDataTransformer houseDataTransformer;

    @InjectMocks
    private HouseDataService underTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetHouseDataListWhenThereIsNoHouseInDb() {
        // GIVEN
        BDDMockito.given(houseService.listHouses()).willReturn(new ArrayList<House>());
        // WHEN
        List<HouseData> result = underTest.getHouseDataList();
        // THEN
        Assert.assertEquals(Collections.EMPTY_LIST, result);
    }

    @Test
    public void testGetHouseDataListWhenThereAreMoreHousesInDb() {
        // GIVEN
        List<House> houseList = createHouseList();
        List<HouseData> houseDataList = createHouseDataList(houseList);
        BDDMockito.given(houseService.listHouses()).willReturn(houseList);
        BDDMockito.when(houseDataTransformer.transformToHouseData(BDDMockito.isA(House.class)))
                .thenReturn(houseDataList.get(0), houseDataList.get(1));
        // WHEN
        List<HouseData> result = underTest.getHouseDataList();
        // THEN
        BDDMockito.verify(houseService).listHouses();
        BDDMockito.verify(houseDataTransformer, BDDMockito.atLeast(2))
                .transformToHouseData(BDDMockito.isA(House.class));
        Assert.assertEquals(houseDataList, result);
        Assert.assertEquals(houseDataList.size(), result.size());
        Assert.assertEquals(houseDataList.get(0), result.get(0));
        Assert.assertEquals(houseDataList.get(1), result.get(1));
    }

    @Test
    public void testGetHouseDataByNameWhenHouseCannotBeFoundInDb() {
        // GIVEN
        String name = "name";
        House house = null;
        HouseData houseData = null;
        BDDMockito.given(houseService.getHouseByName(name)).willReturn(house);
        BDDMockito.given(houseDataTransformer.transformToHouseData(house)).willReturn(houseData);
        // WHEN
        HouseData result = underTest.getHouseDataByName(name);
        // THEN
        BDDMockito.verify(houseService).getHouseByName(name);
        BDDMockito.verify(houseDataTransformer).transformToHouseData(house);
        Assert.assertNull(result);
    }

    @Test
    public void testGetHouseDataByNameWhenHouseCanBeFoundInDb() {
        // GIVEN
        String name = "name";
        House house = House.createHouse("name", "slogan");
        house.setHouseId(1);
        HouseData houseData = new HouseDataTransformer().transformToHouseData(house);
        BDDMockito.given(houseService.getHouseByName(name)).willReturn(house);
        BDDMockito.given(houseDataTransformer.transformToHouseData(house)).willReturn(houseData);
        // WHEN
        HouseData result = underTest.getHouseDataByName(name);
        // THEN
        BDDMockito.verify(houseService).getHouseByName(name);
        BDDMockito.verify(houseDataTransformer).transformToHouseData(house);
        Assert.assertEquals(houseData, result);
    }

    private List<House> createHouseList() {
        List<House> houseList = new ArrayList<>();
        House house1 = House.createHouse("house1", "slogan1");
        House house2 = House.createHouse("house2", "slogan2");
        house1.setHouseId(1);
        house2.setHouseId(2);
        houseList.add(house1);
        houseList.add(house2);
        return houseList;
    }

    private List<HouseData> createHouseDataList(List<House> houseList) {
        List<HouseData> houseDataList = new ArrayList<>();
        HouseDataTransformer transformer = new HouseDataTransformer();
        for (House house : houseList) {
            houseDataList.add(transformer.transformToHouseData(house));
        }
        return houseDataList;
    }

}
