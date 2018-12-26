package hu.elte.progtech.spynet.house;

import hu.elte.progtech.spynet.dal.house.HouseDto;
import hu.elte.progtech.spynet.domain.house.House;
import hu.elte.progtech.spynet.domain.house.HouseTransformer;
import org.junit.Assert;
import org.junit.Test;

public class HouseTransformerTest {

    private HouseTransformer underTest = new HouseTransformer();

    @Test
    public void testTransformToHouseDtoWhenInputIsNull() {
        // GIVEN
        // WHEN
        HouseDto result = underTest.transformToHouseDto(null);
        // THEN
        Assert.assertNull(result);
    }

    @Test
    public void testTransformToHouseDtoWhenInputIsValidNull() {
        // GIVEN
        House house = House.createHouse("house", "slogan");
        HouseDto houseDto = HouseUtil.createHouseDto(house);
        // WHEN
        HouseDto result = underTest.transformToHouseDto(house);
        // THEN
        Assert.assertEquals(houseDto, result);
    }

    @Test
    public void testTransformFromHouseDtoWhenInputIsNull() {
        // GIVEN
        // WHEN
        House result = underTest.transformFromHouseDto(null);
        // THEN
        Assert.assertNull(result);
    }

    @Test
    public void testTransformFromHouseDtoWhenInputIsValidNull() {
        // GIVEN
        House house = House.createHouse("house", "slogan");
        HouseDto houseDto = HouseUtil.createHouseDto(house);
        // WHEN
        House result = underTest.transformFromHouseDto(houseDto);
        // THEN
        Assert.assertEquals(house, result);
    }

}
