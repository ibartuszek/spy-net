package hu.elte.progtech.spynet.presentation.fellowship.modify;

import hu.elte.progtech.spynet.domain.fellowship.Fellowship;
import hu.elte.progtech.spynet.domain.fellowship.FellowshipService;
import hu.elte.progtech.spynet.domain.house.House;
import hu.elte.progtech.spynet.presentation.fellowship.FellowshipData;
import hu.elte.progtech.spynet.presentation.fellowship.FellowshipDataService;
import hu.elte.progtech.spynet.presentation.fellowship.FellowshipDataTransformer;
import hu.elte.progtech.spynet.presentation.fellowship.create.FellowshipRequest;
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

public class ModifyFellowshipDataServiceTest {

    @Mock
    private FellowshipService fellowshipService;

    @Mock
    private FellowshipDataService fellowshipDataService;

    @Mock
    private FellowshipDataTransformer fellowshipDataTransformer;

    private DateTimeFormatter dateTimeFormatter;

    @InjectMocks
    private ModifyFellowshipDataService underTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        underTest.setDateTimeFormatter(dateTimeFormatter);
    }

    @Test
    public void testCreateFellowshipRequestWhenIdIsZero() {
        // GIVEN
        ModifyFellowshipRequest modifyFellowshipRequest = new ModifyFellowshipRequest();
        modifyFellowshipRequest.setFellowshipId(0);
        // WHEN
        FellowshipRequest result = underTest.createFellowshipRequest(modifyFellowshipRequest);
        // THEN
        Assert.assertNull(result.getHouse1());
        Assert.assertNull(result.getHouse2());
        Assert.assertNull(result.getBegin());
        Assert.assertNull(result.getEnd());
    }

    @Test
    public void testCreateFellowshipRequestWhenFellowshipCannotBeFoundInDb() {
        // GIVEN
        ModifyFellowshipRequest modifyFellowshipRequest = new ModifyFellowshipRequest();
        modifyFellowshipRequest.setFellowshipId(1);
        FellowshipRequest fellowshipRequest = null;
        Fellowship fellowship = null;
        BDDMockito.given(fellowshipService.findById(modifyFellowshipRequest.getFellowshipId())).willReturn(fellowship);
        BDDMockito.given(fellowshipDataTransformer.transformToFellowshipRequest(fellowship)).willReturn(fellowshipRequest);
        // WHEN
        FellowshipRequest result = underTest.createFellowshipRequest(modifyFellowshipRequest);
        // THEN
        BDDMockito.verify(fellowshipService).findById(modifyFellowshipRequest.getFellowshipId());
        BDDMockito.verify(fellowshipDataTransformer).transformToFellowshipRequest(fellowship);
        Assert.assertNull(result);
    }

    @Test
    public void testCreateFellowshipRequestWhenFellowshipCanBeFoundInDb() {
        // GIVEN
        ModifyFellowshipRequest modifyFellowshipRequest = new ModifyFellowshipRequest();
        modifyFellowshipRequest.setFellowshipId(1);
        FellowshipRequest fellowshipRequest = new FellowshipRequest();
        fellowshipRequest.setHouse1("house1");
        fellowshipRequest.setHouse2("house2");
        fellowshipRequest.setBegin("1111-11-11");
        fellowshipRequest.setEnd("");
        House house1 = House.createHouse(fellowshipRequest.getHouse1(), "slogan1");
        House house2 = House.createHouse(fellowshipRequest.getHouse2(), "slogan2");
        Fellowship fellowship = Fellowship.createFellowship(house1, house2, LocalDate.parse(fellowshipRequest.getBegin(), dateTimeFormatter));
        BDDMockito.given(fellowshipService.findById(modifyFellowshipRequest.getFellowshipId())).willReturn(fellowship);
        BDDMockito.given(fellowshipDataTransformer.transformToFellowshipRequest(fellowship)).willReturn(fellowshipRequest);
        // WHEN
        FellowshipRequest result = underTest.createFellowshipRequest(modifyFellowshipRequest);
        // THEN
        BDDMockito.verify(fellowshipService).findById(modifyFellowshipRequest.getFellowshipId());
        BDDMockito.verify(fellowshipDataTransformer).transformToFellowshipRequest(fellowship);
        Assert.assertEquals(fellowshipRequest, result);
    }

    @Test
    public void testSaveFellowshipWhenFellowshipHasEndedAlready() {
        // GIVEN
        FellowshipRequest fellowshipRequest = new FellowshipRequest();
        fellowshipRequest.setHouse1("house1");
        fellowshipRequest.setHouse2("house2");
        fellowshipRequest.setBegin("1111-11-11");
        fellowshipRequest.setEnd("1111-12-12");
        House house1 = House.createHouse(fellowshipRequest.getHouse1(), "slogan1");
        House house2 = House.createHouse(fellowshipRequest.getHouse2(), "slogan2");
        Fellowship fellowship = Fellowship.createFellowship(house1, house2, LocalDate.parse(fellowshipRequest.getBegin(), dateTimeFormatter));
        fellowship.setEnd(LocalDate.now());
        List<FellowshipData> fellowshipDataList = new ArrayList<>();
        fellowshipDataList.add(new FellowshipDataTransformer().transformToFellowshipData(fellowship));
        BDDMockito.given(fellowshipDataService.getFellowshipDataList()).willReturn(fellowshipDataList);
        // WHEN
        boolean result = underTest.saveFellowship(fellowshipRequest);
        // THEN
        BDDMockito.verify(fellowshipDataService).getFellowshipDataList();
        Assert.assertFalse(result);
    }

    @Test
    public void testSaveFellowshipWhenFellowshipCanModify() {
        // GIVEN
        long id = 1;
        FellowshipRequest fellowshipRequest = new FellowshipRequest();
        fellowshipRequest.setHouse1("house1");
        fellowshipRequest.setHouse2("house2");
        fellowshipRequest.setBegin("1111-11-11");
        fellowshipRequest.setEnd("1111-12-12");
        House house1 = House.createHouse(fellowshipRequest.getHouse1(), "slogan1");
        House house2 = House.createHouse(fellowshipRequest.getHouse2(), "slogan2");
        Fellowship fellowship = Fellowship.createFellowship(house1, house2, LocalDate.parse(fellowshipRequest.getBegin(), dateTimeFormatter));
        fellowship.setFellowshipId(id);
        List<FellowshipData> fellowshipDataList = new ArrayList<>();
        fellowshipDataList.add(new FellowshipDataTransformer().transformToFellowshipData(fellowship));
        BDDMockito.given(fellowshipDataService.getFellowshipDataList()).willReturn(fellowshipDataList);
        BDDMockito.given(fellowshipService.findById(id)).willReturn(fellowship);
        BDDMockito.doNothing().when(fellowshipService).updateFellowship(fellowship);
        // WHEN
        boolean result = underTest.saveFellowship(fellowshipRequest);
        // THEN
        BDDMockito.verify(fellowshipDataService).getFellowshipDataList();
        BDDMockito.verify(fellowshipService).findById(id);
        BDDMockito.verify(fellowshipService).updateFellowship(fellowship);
        Assert.assertTrue(result);
        Assert.assertEquals("1111-12-12", fellowship.getEnd().toString());
    }

}
