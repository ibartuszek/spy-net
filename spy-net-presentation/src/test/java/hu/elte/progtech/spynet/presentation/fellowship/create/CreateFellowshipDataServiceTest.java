package hu.elte.progtech.spynet.presentation.fellowship.create;

import hu.elte.progtech.spynet.domain.fellowship.Fellowship;
import hu.elte.progtech.spynet.domain.fellowship.FellowshipService;
import hu.elte.progtech.spynet.domain.house.House;
import hu.elte.progtech.spynet.presentation.fellowship.FellowshipData;
import hu.elte.progtech.spynet.presentation.fellowship.FellowshipDataService;
import hu.elte.progtech.spynet.presentation.fellowship.FellowshipDataTransformer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CreateFellowshipDataServiceTest {

    @Mock
    private FellowshipService fellowshipService;

    @Mock
    private FellowshipDataTransformer fellowshipDataTransformer;

    @Mock
    private FellowshipDataService fellowshipDataService;

    @InjectMocks
    private CreateFellowshipDataService underTest;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCheckRequestHousesWhenInputHasSameHouses() {
        // GIVEN
        FellowshipRequest fellowshipRequest = new FellowshipRequest();
        fellowshipRequest.setHouse1("house1");
        fellowshipRequest.setHouse2("house1");
        fellowshipRequest.setBegin("1111-11-11");
        // WHEN
        boolean result = underTest.checkRequestHouses(fellowshipRequest);
        // THEN
        Assert.assertTrue(result);
    }

    @Test
    public void testCheckRequestHousesWhenInputHasDifferentHouses() {
        // GIVEN
        FellowshipRequest fellowshipRequest = new FellowshipRequest();
        fellowshipRequest.setHouse1("house1");
        fellowshipRequest.setHouse2("house2");
        fellowshipRequest.setBegin("1111-11-11");
        // WHEN
        boolean result = underTest.checkRequestHouses(fellowshipRequest);
        // THEN
        Assert.assertFalse(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveFellowshipWhenInputIsNull() {
        // GIVEN
        FellowshipRequest fellowshipRequest = null;
        // WHEN
        boolean result = underTest.saveFellowship(fellowshipRequest);
        // THEN
        Assert.fail("IllegalArgumentException should be thrown!");
    }

    @Test
    public void testSaveFellowshipWhenInputHasBeenSavedAlready() {
        // GIVEN
        FellowshipRequest fellowshipRequest = new FellowshipRequest();
        fellowshipRequest.setHouse1("house1");
        fellowshipRequest.setHouse2("house2");
        fellowshipRequest.setBegin("1111-11-11");
        fellowshipRequest.setEnd("");
        FellowshipData fellowshipData = new FellowshipData();
        fellowshipData.setHouse1("house1");
        fellowshipData.setHouse2("house2");
        fellowshipData.setBegin("1111-11-11");
        fellowshipData.setEnd("");
        List<FellowshipData> fellowshipDataList = new ArrayList<>();
        fellowshipDataList.add(fellowshipData);
        BDDMockito.given(fellowshipDataService.getFellowshipDataList()).willReturn(fellowshipDataList);
        // WHEN
        boolean result = underTest.saveFellowship(fellowshipRequest);
        // THEN
        BDDMockito.verify(fellowshipDataService).getFellowshipDataList();
        Assert.assertFalse(result);
    }

    @Test
    public void testSaveFellowshipWhenFellowshipHasNotEndedYet() {
        // GIVEN
        FellowshipRequest fellowshipRequest = new FellowshipRequest();
        fellowshipRequest.setHouse1("house1");
        fellowshipRequest.setHouse2("house2");
        fellowshipRequest.setBegin("1111-11-13");
        fellowshipRequest.setEnd("");
        FellowshipData fellowshipData = new FellowshipData();
        fellowshipData.setHouse1("house1");
        fellowshipData.setHouse2("house2");
        fellowshipData.setBegin("1111-11-11");
        fellowshipData.setEnd("");
        List<FellowshipData> fellowshipDataList = new ArrayList<>();
        fellowshipDataList.add(fellowshipData);
        BDDMockito.given(fellowshipDataService.getFellowshipDataList()).willReturn(fellowshipDataList);
        // WHEN
        boolean result = underTest.saveFellowship(fellowshipRequest);
        // THEN
        BDDMockito.verify(fellowshipDataService).getFellowshipDataList();
        Assert.assertFalse(result);
    }

    @Test
    public void testSaveFellowshipWhenInputHasNotBeenSavedYet() {
        // GIVEN
        FellowshipRequest fellowshipRequest = new FellowshipRequest();
        fellowshipRequest.setHouse1("house1");
        fellowshipRequest.setHouse2("house3");
        fellowshipRequest.setBegin("1111-11-11");
        fellowshipRequest.setEnd("");
        FellowshipData fellowshipData = new FellowshipData();
        fellowshipData.setHouse1("house1");
        fellowshipData.setHouse2("house2");
        fellowshipData.setBegin("1111-11-11");
        fellowshipData.setEnd("");
        House house1 = House.createHouse("house1", "slogan1");
        House house3 = House.createHouse("house3", "slogan3");
        Fellowship fellowship = Fellowship.createFellowship(house1, house3, LocalDate.parse(fellowshipRequest.getBegin(), dateTimeFormatter));
        List<FellowshipData> fellowshipDataList = new ArrayList<>();
        fellowshipDataList.add(fellowshipData);
        BDDMockito.given(fellowshipDataService.getFellowshipDataList()).willReturn(fellowshipDataList);
        BDDMockito.given(fellowshipDataTransformer.transformFromFellowshipRequest(fellowshipRequest)).willReturn(fellowship);
        BDDMockito.doNothing().when(fellowshipService).saveFellowship(fellowship);
        // WHEN
        boolean result = underTest.saveFellowship(fellowshipRequest);
        // THEN
        BDDMockito.verify(fellowshipDataService).getFellowshipDataList();
        BDDMockito.verify(fellowshipDataTransformer).transformFromFellowshipRequest(fellowshipRequest);
        BDDMockito.verify(fellowshipService).saveFellowship(fellowship);
        Assert.assertTrue(result);
    }

    @Test
    public void testSaveFellowshipWhenInputHasBeenEndedAlready() {
        // GIVEN
        FellowshipRequest fellowshipRequest = new FellowshipRequest();
        fellowshipRequest.setHouse1("house1");
        fellowshipRequest.setHouse2("house2");
        fellowshipRequest.setBegin("1111-11-13");
        fellowshipRequest.setEnd("");
        FellowshipData fellowshipData = new FellowshipData();
        fellowshipData.setHouse1("house1");
        fellowshipData.setHouse2("house2");
        fellowshipData.setBegin("1111-11-11");
        fellowshipData.setEnd("1111-11-12");
        House house1 = House.createHouse("house1", "slogan1");
        House house3 = House.createHouse("house3", "slogan3");
        Fellowship fellowship = Fellowship.createFellowship(house1, house3, LocalDate.parse(fellowshipRequest.getBegin(), dateTimeFormatter));
        List<FellowshipData> fellowshipDataList = new ArrayList<>();
        fellowshipDataList.add(fellowshipData);
        BDDMockito.given(fellowshipDataService.getFellowshipDataList()).willReturn(fellowshipDataList);
        BDDMockito.given(fellowshipDataTransformer.transformFromFellowshipRequest(fellowshipRequest)).willReturn(fellowship);
        BDDMockito.doNothing().when(fellowshipService).saveFellowship(fellowship);
        // WHEN
        boolean result = underTest.saveFellowship(fellowshipRequest);
        // THEN
        BDDMockito.verify(fellowshipDataService).getFellowshipDataList();
        BDDMockito.verify(fellowshipDataTransformer).transformFromFellowshipRequest(fellowshipRequest);
        BDDMockito.verify(fellowshipService).saveFellowship(fellowship);
        Assert.assertTrue(result);
    }

}
