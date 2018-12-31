package hu.elte.progtech.spynet.presentation.fellowship;

import hu.elte.progtech.spynet.domain.fellowship.Fellowship;
import hu.elte.progtech.spynet.domain.house.House;
import hu.elte.progtech.spynet.presentation.fellowship.create.FellowshipRequest;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FellowshipDataTransformerTest {

    @Mock
    private HouseDataService houseDataService;

    @Mock
    private HouseDataTransformer houseDataTransformer;

    private DateTimeFormatter dateTimeFormatter;

    @InjectMocks
    private FellowshipDataTransformer underTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        underTest.setDateTimeFormatter(dateTimeFormatter);
    }

    @Test
    public void testTransformToFellowshipDataWhenInputIsNull() {
        // GIVEN
        Fellowship fellowship = null;
        // WHEN
        FellowshipData result = underTest.transformToFellowshipData(fellowship);
        // THEN
        Assert.assertNull(result);
    }

    @Test
    public void testTransformToFellowshipDataWhenInputIsValidWithoutEnd() {
        // GIVEN
        Fellowship fellowship = createFellowshipWithoutEnd();
        FellowshipData fellowshipData = new FellowshipData();
        fellowshipData.setFellowshipId(fellowship.getFellowshipId());
        fellowshipData.setHouse1(fellowship.getHouse1().getName());
        fellowshipData.setHouse2(fellowship.getHouse2().getName());
        fellowshipData.setBegin(fellowship.getBegin().toString());
        fellowshipData.setEnd("");
        fellowshipData.setFellowshipUrl(String.format("%s?fellowshipId=%d",
                ModifyFellowshipDataController.REQUEST_MAPPING, fellowshipData.getFellowshipId()));
        // WHEN
        FellowshipData result = underTest.transformToFellowshipData(fellowship);
        // THEN
        Assert.assertEquals(fellowshipData, result);
    }

    @Test
    public void testTransformToFellowshipDataWhenInputIsValidWithEnd() {
        // GIVEN
        Fellowship fellowship = createFellowshipWithoutEnd();
        fellowship.setEnd(LocalDate.now());
        FellowshipData fellowshipData = new FellowshipData();
        fellowshipData.setFellowshipId(fellowship.getFellowshipId());
        fellowshipData.setHouse1(fellowship.getHouse1().getName());
        fellowshipData.setHouse2(fellowship.getHouse2().getName());
        fellowshipData.setBegin(fellowship.getBegin().toString());
        fellowshipData.setEnd(fellowship.getEnd().toString());
        fellowshipData.setFellowshipUrl("");
        // WHEN
        FellowshipData result = underTest.transformToFellowshipData(fellowship);
        // THEN
        Assert.assertEquals(fellowshipData, result);
    }

    @Test
    public void testTransformFromFellowshipRequestWhenInputIsNull() {
        // GIVEN
        FellowshipRequest fellowshipRequest = null;
        // WHEN
        Fellowship result = underTest.transformFromFellowshipRequest(fellowshipRequest);
        // THEN
        Assert.assertNull(result);
    }

    @Test
    public void testTransformFromFellowshipRequestWhenInputIsValidWithoutEnd() {
        // GIVEN
        FellowshipRequest fellowshipRequest = createFellowshipRequestWithoutEnd();
        fellowshipRequest.setEnd("");
        House house1 = House.createHouse(fellowshipRequest.getHouse1(), "slogan1");
        House house2 = House.createHouse(fellowshipRequest.getHouse2(), "slogan2");
        Fellowship fellowship = Fellowship.createFellowship(house1, house2, LocalDate.parse(fellowshipRequest.getBegin(), dateTimeFormatter));
        HouseDataTransformer transformer = new HouseDataTransformer();
        HouseData houseData1 = transformer.transformToHouseData(house1);
        HouseData houseData2 = transformer.transformToHouseData(house2);
        BDDMockito.when(houseDataService.getHouseDataByName(BDDMockito.isA(String.class)))
                .thenReturn(houseData1, houseData2);
        BDDMockito.when(houseDataTransformer.transformFromHouseData(BDDMockito.isA(HouseData.class)))
                .thenReturn(house1, house2);
        // WHEN
        Fellowship result = underTest.transformFromFellowshipRequest(fellowshipRequest);
        // THEN
        BDDMockito.verify(houseDataService, BDDMockito.atLeast(2))
                .getHouseDataByName(BDDMockito.isA(String.class));
        BDDMockito.verify(houseDataTransformer, BDDMockito.atLeast(2))
                .transformFromHouseData(BDDMockito.isA(HouseData.class));
        Assert.assertEquals(fellowship, result);
    }

    @Test
    public void testTransformFromFellowshipRequestWhenInputIsValidWithEnd() {
        // GIVEN
        FellowshipRequest fellowshipRequest = createFellowshipRequestWithoutEnd();
        fellowshipRequest.setEnd("1111-12-12");
        House house1 = House.createHouse(fellowshipRequest.getHouse1(), "slogan1");
        House house2 = House.createHouse(fellowshipRequest.getHouse2(), "slogan2");
        Fellowship fellowship = Fellowship.createFellowship(house1, house2, LocalDate.parse(fellowshipRequest.getBegin(), dateTimeFormatter));
        fellowship.setEnd(LocalDate.parse(fellowshipRequest.getEnd(), dateTimeFormatter));
        HouseDataTransformer transformer = new HouseDataTransformer();
        HouseData houseData1 = transformer.transformToHouseData(house1);
        HouseData houseData2 = transformer.transformToHouseData(house2);
        BDDMockito.when(houseDataService.getHouseDataByName(BDDMockito.isA(String.class)))
                .thenReturn(houseData1, houseData2);
        BDDMockito.when(houseDataTransformer.transformFromHouseData(BDDMockito.isA(HouseData.class)))
                .thenReturn(house1, house2);
        // WHEN
        Fellowship result = underTest.transformFromFellowshipRequest(fellowshipRequest);
        // THEN
        BDDMockito.verify(houseDataService, BDDMockito.atLeast(2))
                .getHouseDataByName(BDDMockito.isA(String.class));
        BDDMockito.verify(houseDataTransformer, BDDMockito.atLeast(2))
                .transformFromHouseData(BDDMockito.isA(HouseData.class));
        Assert.assertEquals(fellowship, result);
    }

    @Test
    public void testTransformToFellowshipRequestWhenInputIsNull() {
        // GIVEN
        Fellowship fellowship = null;
        // WHEN
        FellowshipRequest result = underTest.transformToFellowshipRequest(fellowship);
        // THEN
        Assert.assertNull(result);
    }

    @Test
    public void testTransformToFellowshipRequestWhenInputIsValidWithoutEnd() {
        // GIVEN
        Fellowship fellowship = createFellowshipWithoutEnd();
        FellowshipRequest fellowshipRequest = new FellowshipRequest();
        fellowshipRequest.setHouse1(fellowship.getHouse1().getName());
        fellowshipRequest.setHouse2(fellowship.getHouse2().getName());
        fellowshipRequest.setBegin(fellowship.getBegin().toString());
        fellowshipRequest.setEnd("");
        // WHEN
        FellowshipRequest result = underTest.transformToFellowshipRequest(fellowship);
        // THEN
        Assert.assertEquals(fellowshipRequest, result);
    }

    @Test
    public void testTransformToFellowshipRequestWhenInputIsValidWithEnd() {
        // GIVEN
        Fellowship fellowship = createFellowshipWithoutEnd();
        fellowship.setEnd(LocalDate.now());
        FellowshipRequest fellowshipRequest = new FellowshipRequest();
        fellowshipRequest.setHouse1(fellowship.getHouse1().getName());
        fellowshipRequest.setHouse2(fellowship.getHouse2().getName());
        fellowshipRequest.setBegin(fellowship.getBegin().toString());
        fellowshipRequest.setEnd(fellowship.getEnd().toString());
        // WHEN
        FellowshipRequest result = underTest.transformToFellowshipRequest(fellowship);
        // THEN
        Assert.assertEquals(fellowshipRequest, result);
    }

    private Fellowship createFellowshipWithoutEnd() {
        Fellowship fellowship;
        House house1 = House.createHouse("house1", "slogan1");
        House house2 = House.createHouse("house2", "slogan2");
        fellowship = Fellowship.createFellowship(house1, house2, LocalDate.now());
        fellowship.setFellowshipId(1);
        return fellowship;
    }

    private FellowshipRequest createFellowshipRequestWithoutEnd() {
        FellowshipRequest fellowshipRequest = new FellowshipRequest();
        fellowshipRequest.setHouse1("house1");
        fellowshipRequest.setHouse2("house2");
        fellowshipRequest.setBegin("1111-11-11");
        return fellowshipRequest;
    }

}
