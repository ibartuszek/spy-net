package hu.elte.progtech.spynet.presentation.fellowship;

import hu.elte.progtech.spynet.domain.fellowship.Fellowship;
import hu.elte.progtech.spynet.domain.fellowship.FellowshipService;
import hu.elte.progtech.spynet.domain.house.House;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FellowshipDataServiceTest {
    @Mock
    private FellowshipService fellowshipService;

    @Mock
    private FellowshipDataTransformer fellowshipDataTransformer;

    @InjectMocks
    private FellowshipDataService underTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetFellowshipDataListWhenThereIsNoElementInDb() {
        // GIVEN
        List<Fellowship> fellowshipList = new ArrayList<>();
        BDDMockito.given(fellowshipService.listFellowships()).willReturn(fellowshipList);
        // WHEN
        List<FellowshipData> result = underTest.getFellowshipDataList();
        // THEN
        BDDMockito.verify(fellowshipService).listFellowships();
        Assert.assertEquals(Collections.EMPTY_LIST, result);
    }

    @Test
    public void testGetFellowshipDataListWhenThereAreSomeElementsInDb() {
        // GIVEN
        List<Fellowship> fellowshipList = createFellowshipList();
        List<FellowshipData> fellowshipDataList = createFellowshipDataList(fellowshipList);
        BDDMockito.given(fellowshipService.listFellowships()).willReturn(fellowshipList);
        BDDMockito.when(fellowshipDataTransformer.transformToFellowshipData(BDDMockito.isA(Fellowship.class)))
                .thenReturn(fellowshipDataList.get(0), fellowshipDataList.get(1));
        // WHEN
        List<FellowshipData> result = underTest.getFellowshipDataList();
        // THEN
        BDDMockito.verify(fellowshipService).listFellowships();
        BDDMockito.verify(fellowshipDataTransformer, BDDMockito.atLeast(2))
                .transformToFellowshipData(BDDMockito.isA(Fellowship.class));
        Assert.assertEquals(fellowshipDataList, result);
    }

    private List<Fellowship> createFellowshipList() {
        List<Fellowship> fellowshipList = new ArrayList<>();
        House house1 = House.createHouse("house1", "slogan1");
        House house2 = House.createHouse("house2", "slogan2");
        House house3 = House.createHouse("house3", "slogan3");
        Fellowship fellowship1 = Fellowship.createFellowship(house1, house2, LocalDate.now());
        Fellowship fellowship2 = Fellowship.createFellowship(house1, house3, LocalDate.now());
        fellowshipList.add(fellowship1);
        fellowshipList.add(fellowship2);
        return fellowshipList;
    }

    private List<FellowshipData> createFellowshipDataList(List<Fellowship> fellowshipList) {
        List<FellowshipData> fellowshipDataList = new ArrayList<>();
        FellowshipDataTransformer transformer = new FellowshipDataTransformer();
        for (Fellowship fellowship : fellowshipList) {
            fellowshipDataList.add(transformer.transformToFellowshipData(fellowship));
        }
        return fellowshipDataList;
    }

}
