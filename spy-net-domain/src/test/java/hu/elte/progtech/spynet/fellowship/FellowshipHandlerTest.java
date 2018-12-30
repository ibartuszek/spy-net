package hu.elte.progtech.spynet.fellowship;

import hu.elte.progtech.spynet.dal.fellowship.FellowshipDao;
import hu.elte.progtech.spynet.dal.fellowship.FellowshipDto;
import hu.elte.progtech.spynet.domain.fellowship.Fellowship;
import hu.elte.progtech.spynet.domain.fellowship.FellowshipHandler;
import hu.elte.progtech.spynet.domain.fellowship.FellowshipTransformer;
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
import java.util.List;
import java.util.Optional;

public class FellowshipHandlerTest {

    @Mock
    private FellowshipDao fellowshipDao;

    @Mock
    private FellowshipTransformer fellowshipTransformer;

    @InjectMocks
    private FellowshipHandler underTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSaveFellowshipWhenInputIsNull() {
        // GIVEN
        Fellowship fellowship = null;
        // WHEN
        underTest.saveFellowship(fellowship);
        // THEN
        Assert.fail("IllegalArgumentException should be thrown!");
    }

    @Test
    public void testSaveFellowshipWhenInputIsValid() {
        // GIVEN
        House house1 = House.createHouse("house1", "slogan1");
        House house2 = House.createHouse("house2", "slogan2");
        Fellowship fellowship = Fellowship.createFellowship(house1, house2, LocalDate.now());
        FellowshipDto fellowshipDto = FellowshipUtil.createFellowshipDto(fellowship);
        BDDMockito.given(fellowshipTransformer.transformToFellowshipDto(fellowship))
                .willReturn(fellowshipDto);
        BDDMockito.doNothing().when(fellowshipDao).saveFellowship(BDDMockito.isA(FellowshipDto.class));
        // WHEN
        underTest.saveFellowship(fellowship);
        // THEN
        BDDMockito.verify(fellowshipTransformer).transformToFellowshipDto(fellowship);
        BDDMockito.verify(fellowshipDao).saveFellowship(BDDMockito.isA(FellowshipDto.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateFellowshipWhenInputIsNull() {
        // GIVEN
        Fellowship fellowship = null;
        // WHEN
        underTest.updateFellowship(fellowship);
        // THEN
        Assert.fail("IllegalArgumentException should be thrown!");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateFellowshipWhenFellowshipDoesNotHaveEndDate() {
        // GIVEN
        House house1 = House.createHouse("house1", "slogan1");
        House house2 = House.createHouse("house2", "slogan2");
        Fellowship fellowship = Fellowship.createFellowship(house1, house2, LocalDate.now());
        // WHEN
        underTest.updateFellowship(fellowship);
        // THEN
        Assert.fail("IllegalArgumentException should be thrown!");
    }

    @Test
    public void testUpdateFellowshipWhenInputIsLegal() {
        // GIVEN
        House house1 = House.createHouse("house1", "slogan1");
        House house2 = House.createHouse("house2", "slogan2");
        Fellowship fellowship = Fellowship.createFellowship(house1, house2, LocalDate.now());
        fellowship.setEnd(LocalDate.now());
        FellowshipDto fellowshipDto = FellowshipUtil.createFellowshipDto(fellowship);
        BDDMockito.given(fellowshipTransformer.transformToFellowshipDto(fellowship))
                .willReturn(fellowshipDto);
        BDDMockito.doNothing().when(fellowshipDao).updateFellowship(BDDMockito.isA(FellowshipDto.class));
        // WHEN
        underTest.updateFellowship(fellowship);
        // THEN
        BDDMockito.verify(fellowshipTransformer).transformToFellowshipDto(fellowship);
        BDDMockito.verify(fellowshipDao).updateFellowship(BDDMockito.isA(FellowshipDto.class));
    }

    @Test
    public void testListFellowshipsWhenThereIsNoElementInDb(){
        // GIVEN
        List<Fellowship> fellowshipList = new ArrayList<>();
        BDDMockito.given(fellowshipDao.listFellowships()).willReturn(new ArrayList<>());
        // WHEN
        List<Fellowship> resultList = underTest.listFellowships();
        // THEN
        Assert.assertEquals(fellowshipList, resultList);
    }

    @Test
    public void testListFellowshipsWhenThereAreMoreElementsInDb(){
        // GIVEN
        List<Fellowship> fellowshipList = new ArrayList<>();
        House house1 = House.createHouse("name1", "slogan1");
        House house2 = House.createHouse("name2", "slogan2");
        House house3 = House.createHouse("name3", "slogan3");
        Fellowship fellowship1 = Fellowship.createFellowship(house1, house2, LocalDate.now());
        Fellowship fellowship2 = Fellowship.createFellowship(house2, house3, LocalDate.now());
        fellowshipList.add(fellowship1);
        fellowshipList.add(fellowship2);
        List<FellowshipDto> fellowshipDtoList = new ArrayList<>();
        FellowshipDto fellowshipDto1 = FellowshipUtil.createFellowshipDto(fellowship1);
        FellowshipDto fellowshipDto2 = FellowshipUtil.createFellowshipDto(fellowship2);
        fellowshipDtoList.add(fellowshipDto1);
        fellowshipDtoList.add(fellowshipDto2);
        BDDMockito.given(fellowshipDao.listFellowships()).willReturn(fellowshipDtoList);
        BDDMockito.when(fellowshipTransformer.transformFromFellowshipDto(BDDMockito.isA(FellowshipDto.class)))
                .thenReturn(fellowship1, fellowship2);
        // WHEN
        List<Fellowship> resultList = underTest.listFellowships();
        // THEN
        BDDMockito.verify(fellowshipDao).listFellowships();
        BDDMockito.verify(fellowshipTransformer, BDDMockito.atLeast(2))
                .transformFromFellowshipDto(BDDMockito.isA(FellowshipDto.class));
        Assert.assertEquals(fellowshipList, resultList);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testFindByIdWhenInputIsZero() {
        // GIVEN
        // WHEN
        Fellowship result = underTest.findById(0);
        // THEN
        Assert.fail("IllegalArgumentException should be thrown!");
    }

    @Test
    public void testFindByIdWhenFellowshipCannotBeFoundInDb() {
        // GIVEN
        long id = 1;
        Fellowship fellowship = null;
        FellowshipDto fellowshipDto = null;
        BDDMockito.given(fellowshipDao.findById(id)).willReturn(fellowshipDto);
        BDDMockito.given(fellowshipTransformer.transformFromFellowshipDto(fellowshipDto))
                .willReturn(fellowship);
        // WHEN
        Fellowship result = underTest.findById(id);
        // THEN
        BDDMockito.verify(fellowshipDao).findById(id);
        BDDMockito.verify(fellowshipTransformer).transformFromFellowshipDto(fellowshipDto);
        Assert.assertEquals(fellowship, result);
    }

    @Test
    public void testFindByIdWhenInputIsValid() {
        // GIVEN
        long id = 1;
        House house1 = House.createHouse("name1", "slogan1");
        House house2 = House.createHouse("name2", "slogan2");
        Fellowship fellowship = Fellowship.createFellowship(house1, house2, LocalDate.now());
        fellowship.setFellowshipId(id);
        FellowshipDto fellowshipDto = FellowshipUtil.createFellowshipDto(fellowship);
        BDDMockito.given(fellowshipDao.findById(id)).willReturn(fellowshipDto);
        BDDMockito.given(fellowshipTransformer.transformFromFellowshipDto(fellowshipDto))
                .willReturn(fellowship);
        // WHEN
        Fellowship result = underTest.findById(id);
        // THEN
        BDDMockito.verify(fellowshipDao).findById(id);
        BDDMockito.verify(fellowshipTransformer).transformFromFellowshipDto(fellowshipDto);
        Assert.assertEquals(fellowship, result);
    }
}
