package hu.elte.progtech.spynet.presentation.house;

import hu.elte.progtech.spynet.domain.house.House;
import hu.elte.progtech.spynet.presentation.house.create.HouseRequest;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class HouseDataTransformerTest {

    private HouseDataTransformer underTest = new HouseDataTransformer();

    @Test
    public void testTransformToHouseDataWhenInputIsNull() {
        // GIVEN
        House house = null;
        // WHEN
        HouseData result = underTest.transformToHouseData(house);
        // THEN
        Assert.assertNull(result);
    }

    @Test
    public void testTransformToHouseDataWhenInputIsNotNull() {
        // GIVEN
        House house = House.createHouse("house", "slogan");
        house.setHouseId(1);
        house.setCrest(null);
        HouseData houseData = new HouseData();
        houseData.setName(house.getName());
        houseData.setSlogan(house.getSlogan());
        houseData.setHouseId(house.getHouseId());
        houseData.setCrest(house.getCrest());
        houseData.setHasCrest(false);
        houseData.setCrestUrl("");
        // WHEN
        HouseData result = underTest.transformToHouseData(house);
        // THEN
        Assert.assertEquals(houseData, result);
        Assert.assertEquals(houseData.getHouseId(), result.getHouseId());
        Assert.assertFalse(result.isHasCrest());
        Assert.assertNull(result.getCrest());
        Assert.assertEquals(null, result.getCrestUrl());
    }

    @Test
    public void testTransformToHouseDataWhenInputIsNotNullHasCrest() {
        // GIVEN
        House house = House.createHouse("house", "slogan");
        house.setHouseId(1);
        house.setCrest(new byte[16]);
        HouseData houseData = new HouseData();
        houseData.setName(house.getName());
        houseData.setSlogan(house.getSlogan());
        houseData.setHouseId(house.getHouseId());
        houseData.setCrest(house.getCrest());
        houseData.setHasCrest(true);
        houseData.setCrestUrl(String.format("%s?houseId=%d",
                HouseDataCrestController.REQUEST_MAPPING, house.getHouseId()));
        // WHEN
        HouseData result = underTest.transformToHouseData(house);
        // THEN
        Assert.assertEquals(houseData, result);
        Assert.assertEquals(houseData.getHouseId(), result.getHouseId());
        Assert.assertTrue(result.isHasCrest());
        Assert.assertEquals(houseData.getCrest(), result.getCrest());
        Assert.assertEquals(houseData.getCrestUrl(), result.getCrestUrl());
    }

    @Test
    public void testTransformFromHouseDataWhenInputIsNull() {
        // GIVEN
        HouseData houseData = null;
        //House house = null;
        // WHEN
        House result = underTest.transformFromHouseData(houseData);
        // THEN
        Assert.assertNull(result);
    }

    @Test
    public void testTransformFromHouseDataWhenInputIsNotNull() {
        // GIVEN
        HouseData houseData = new HouseData();
        houseData.setName("house");
        houseData.setSlogan("slogan");
        houseData.setHasCrest(false);
        houseData.setCrest(null);
        houseData.setCrestUrl(null);
        House house = House.createHouse(houseData.getName(), houseData.getSlogan());
        // WHEN
        House result = underTest.transformFromHouseData(houseData);
        // THEN
        Assert.assertEquals(house, result);
    }

    @Test
    public void testTransformFromHouseRequestWhenInputIsNull() {
        // GIVEN
        // WHEN
        House result = underTest.transformFromHouseRequest(null);
        // THEN
        Assert.assertNull(result);
    }

    @Test
    public void testTransformFromHouseRequestWhenInputIsNotNull() {
        // GIVEN
        MultipartFile multipartFile = BDDMockito.mock(MultipartFile.class);
        HouseRequest houseRequest = new HouseRequest();
        houseRequest.setName("house");
        houseRequest.setSlogan("slogan");
        houseRequest.setCrest(multipartFile);
        BDDMockito.given(BDDMockito.mock(MultipartFile.class).isEmpty()).willReturn(true);
        House house = House.createHouse(houseRequest.getName(), houseRequest.getSlogan());
        // WHEN
        House result = underTest.transformFromHouseRequest(houseRequest);
        // THEN
        Assert.assertEquals(house, result);
    }

}
