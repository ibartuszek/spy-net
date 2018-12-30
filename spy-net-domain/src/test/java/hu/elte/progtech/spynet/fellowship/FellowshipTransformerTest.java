package hu.elte.progtech.spynet.fellowship;

import hu.elte.progtech.spynet.dal.fellowship.FellowshipDto;
import hu.elte.progtech.spynet.dal.house.HouseDto;
import hu.elte.progtech.spynet.domain.fellowship.Fellowship;
import hu.elte.progtech.spynet.domain.fellowship.FellowshipTransformer;
import hu.elte.progtech.spynet.domain.house.House;
import hu.elte.progtech.spynet.domain.house.HouseTransformer;
import hu.elte.progtech.spynet.house.HouseUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

public class FellowshipTransformerTest {

    @Mock
    private HouseTransformer houseTransformer;

    @InjectMocks
    private FellowshipTransformer underTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = NullPointerException.class)
    public void testTransformToFellowshipDtoWhenInputIsNull() {
        // GIVEN
        Fellowship fellowship = null;
        // WHEN
        underTest.transformToFellowshipDto(fellowship);
        // THEN
        Assert.fail("NullPointerException should be thrown!");
    }

    @Test
    public void testTransformToFellowshipDtoWhenInputIsValid() {
        // GIVEN
        House house1 = House.createHouse("house1", "slogan1");
        House house2 = House.createHouse("house2", "slogan2");
        Fellowship fellowship = Fellowship.createFellowship(house1, house2, LocalDate.now());
        HouseDto houseDto1 = HouseUtil.createHouseDto(house1);
        HouseDto houseDto2 = HouseUtil.createHouseDto(house2);
        FellowshipDto fellowshipDto = FellowshipUtil.createFellowshipDto(fellowship);
        BDDMockito.given(houseTransformer.transformToHouseDto(house1)).willReturn(houseDto1);
        BDDMockito.given(houseTransformer.transformToHouseDto(house2)).willReturn(houseDto2);
        // WHEN
        FellowshipDto result = underTest.transformToFellowshipDto(fellowship);
        // THEN
        BDDMockito.verify(houseTransformer, BDDMockito.atLeast(2))
                .transformToHouseDto(BDDMockito.isA(House.class));
        Assert.assertEquals(fellowshipDto, result);
    }

    @Test(expected = NullPointerException.class)
    public void testTransformFromFellowshipDtoWhenInputIsNull() {
        // GIVEN
        FellowshipDto fellowshipDto = null;
        // WHEN
        underTest.transformFromFellowshipDto(fellowshipDto);
        // THEN
        Assert.fail("NullPointerException should be thrown!");
    }

    @Test
    public void testTransformFromFellowshipDtoWhenInputIsValid() {
        // GIVEN
        House house1 = House.createHouse("house1", "slogan1");
        House house2 = House.createHouse("house2", "slogan2");
        Fellowship fellowship = Fellowship.createFellowship(house1, house2, LocalDate.now());
        HouseDto houseDto1 = HouseUtil.createHouseDto(house1);
        HouseDto houseDto2 = HouseUtil.createHouseDto(house2);
        FellowshipDto fellowshipDto = new FellowshipDto();
        fellowshipDto.setHouse1(houseDto1);
        fellowshipDto.setHouse2(houseDto2);
        fellowshipDto.setBegin(fellowship.getBegin());
        BDDMockito.when(houseTransformer.transformFromHouseDto(BDDMockito.isA(HouseDto.class)))
                .thenReturn(house1, house2);
        // WHEN
        Fellowship result = underTest.transformFromFellowshipDto(fellowshipDto);
        // THEN
        BDDMockito.verify(houseTransformer, BDDMockito.atLeast(2))
                .transformFromHouseDto(BDDMockito.isA(HouseDto.class));
        Assert.assertEquals(fellowship, result);
    }

}
