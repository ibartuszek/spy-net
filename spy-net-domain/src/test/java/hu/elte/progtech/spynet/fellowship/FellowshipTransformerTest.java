package hu.elte.progtech.spynet.fellowship;

import hu.elte.progtech.spynet.dal.fellowship.FellowshipDto;
import hu.elte.progtech.spynet.dal.house.HouseDto;
import hu.elte.progtech.spynet.domain.fellowship.Fellowship;
import hu.elte.progtech.spynet.domain.fellowship.FellowshipTransformer;
import hu.elte.progtech.spynet.domain.house.House;
import hu.elte.progtech.spynet.domain.house.HouseTransformer;
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
        FellowshipDto fellowshipDto = FellowshipUtil.createFellowshipDto(fellowship);
        BDDMockito.given(houseTransformer.transformToHouseDto(house1)).willReturn(BDDMockito.isA(HouseDto.class));
        BDDMockito.given(houseTransformer.transformToHouseDto(house2)).willReturn(BDDMockito.isA(HouseDto.class));
        // WHEN
        FellowshipDto result = underTest.transformToFellowshipDto(fellowship);
        // THEN
        BDDMockito.verify(houseTransformer, BDDMockito.atLeast(2)).transformToHouseDto(house1);
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
        FellowshipDto fellowshipDto = FellowshipUtil.createFellowshipDto(fellowship);
        BDDMockito.given(houseTransformer.transformFromHouseDto(BDDMockito.isA(HouseDto.class)))
                .willReturn(BDDMockito.isA(House.class));
        // WHEN
        Fellowship result = underTest.transformFromFellowshipDto(fellowshipDto);
        // THEN
        BDDMockito.verify(houseTransformer, BDDMockito.atLeast(2))
                .transformFromHouseDto(BDDMockito.isA(HouseDto.class));
        Assert.assertEquals(fellowship, result);
    }

}
