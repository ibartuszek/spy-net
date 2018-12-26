package hu.elte.progtech.spynet.dal.fellowship;

import hu.elte.progtech.spynet.dal.house.HouseDto;
import hu.elte.progtech.spynet.dal.house.HouseEntity;
import hu.elte.progtech.spynet.dal.house.HouseRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FellowshipDaoTest {

    @Mock
    private FellowshipRepository fellowshipRepository;

    @Mock
    private HouseRepository houseRepository;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private FellowshipDao underTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testListFellowshipsWhenThereAreNotAnyEntities() {
        // GIVEN
        BDDMockito.given(fellowshipRepository.findAll()).willReturn(Collections.emptyList());
        // WHEN
        List<FellowshipDto> resultList = underTest.listFellowships();
        // THEN
        BDDMockito.verify(fellowshipRepository).findAll();
        Assert.assertEquals(resultList, Collections.emptyList());
    }

    @Test
    public void testListFellowshipsWhenThereIsOneEntity() {
        // GIVEN
        HouseEntity exampleHouseEntity1 = HouseEntity.createHouseEntity("house1", "slogan1");
        exampleHouseEntity1.setHouseId(1);
        HouseEntity exampleHouseEntity2 = HouseEntity.createHouseEntity("house2", "slogan2");
        exampleHouseEntity2.setHouseId(2);
        FellowshipEntity fellowshipEntity = FellowshipEntity.createFellowshipEntity(exampleHouseEntity1, exampleHouseEntity2, LocalDate.now());
        fellowshipEntity.setFellowshipId(3);
        List<FellowshipEntity> fellowshipEntityList = new ArrayList<>();
        fellowshipEntityList.add(fellowshipEntity);
        BDDMockito.given(fellowshipRepository.findAll()).willReturn(fellowshipEntityList);
        List<FellowshipDto> fellowshipDtoList = new ArrayList<>();
        fellowshipDtoList.add(new FellowshipDto(fellowshipEntity, new HouseDto(exampleHouseEntity1), new HouseDto(exampleHouseEntity2)));
        // WHEN
        List<FellowshipDto> resultList = underTest.listFellowships();
        // THEN
        BDDMockito.verify(fellowshipRepository).findAll();
        Assert.assertEquals(resultList, fellowshipDtoList);
        Assert.assertEquals(resultList.get(0), fellowshipDtoList.get(0));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testSaveFellowshipThrowIllegalArgumentExceptionWhenInputIsNull() {
        // GIVEN
        FellowshipDto fellowshipDto = null;
        // WHEN
        underTest.saveFellowship(fellowshipDto);
        // THEN
        Assert.fail("IllegalArgumentException should be thrown!");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testSaveFellowshipThrowIllegalArgumentExceptionWhenInputDoesNotHaveHouse1() {
        // GIVEN
        HouseEntity exampleHouseEntity2 = HouseEntity.createHouseEntity("house2", "slogan2");
        exampleHouseEntity2.setHouseId(2);
        FellowshipDto fellowshipDto = FellowshipDto.createFellowshipDto(null, new HouseDto(exampleHouseEntity2), LocalDate.now());
        // WHEN
        underTest.saveFellowship(fellowshipDto);
        // THEN
        Assert.fail("IllegalArgumentException should be thrown!");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testSaveFellowshipThrowIllegalArgumentExceptionWhenInputDoesNotHaveHouse2() {
        // GIVEN
        HouseEntity exampleHouseEntity1 = HouseEntity.createHouseEntity("house1", "slogan1");
        exampleHouseEntity1.setHouseId(1);
        FellowshipDto fellowshipDto = FellowshipDto.createFellowshipDto(new HouseDto(exampleHouseEntity1), null, LocalDate.now());
        // WHEN
        underTest.saveFellowship(fellowshipDto);
        // THEN
        Assert.fail("IllegalArgumentException should be thrown!");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testSaveFellowshipThrowIllegalArgumentExceptionWhenInputDoesNotHaveBegin() {
        // GIVEN
        HouseEntity exampleHouseEntity1 = HouseEntity.createHouseEntity("house1", "slogan1");
        exampleHouseEntity1.setHouseId(1);
        HouseEntity exampleHouseEntity2 = HouseEntity.createHouseEntity("house2", "slogan2");
        exampleHouseEntity2.setHouseId(2);
        FellowshipDto fellowshipDto = FellowshipDto.createFellowshipDto(new HouseDto(exampleHouseEntity1), new HouseDto(exampleHouseEntity2), null);
        // WHEN
        underTest.saveFellowship(fellowshipDto);
        // THEN
        Assert.fail("IllegalArgumentException should be thrown!");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testSaveFellowshipThrowIllegalArgumentExceptionWhenHouse1CannotBeFoundInDB() {
        // GIVEN
        HouseEntity exampleHouseEntity1 = HouseEntity.createHouseEntity("house1", "slogan1");
        exampleHouseEntity1.setHouseId(1);
        HouseEntity exampleHouseEntity2 = HouseEntity.createHouseEntity("house2", "slogan2");
        exampleHouseEntity2.setHouseId(2);
        FellowshipDto fellowshipDto = FellowshipDto.createFellowshipDto(new HouseDto(exampleHouseEntity1), new HouseDto(exampleHouseEntity2), LocalDate.now());
        BDDMockito.given(houseRepository.findById(exampleHouseEntity1.getHouseId()))
                .willReturn(Optional.empty());
        BDDMockito.given(houseRepository.findById(exampleHouseEntity2.getHouseId()))
                .willReturn(Optional.of(exampleHouseEntity2));
        // WHEN
        underTest.saveFellowship(fellowshipDto);
        // THEN
        Assert.fail("IllegalArgumentException should be thrown!");
    }

    @Test
    public void testSaveFellowshipWhenAllInputParameterIsLegal() {
        // GIVEN
        HouseEntity exampleHouseEntity1 = HouseEntity.createHouseEntity("house1", "slogan1");
        exampleHouseEntity1.setHouseId(1);
        HouseEntity exampleHouseEntity2 = HouseEntity.createHouseEntity("house2", "slogan2");
        exampleHouseEntity2.setHouseId(2);
        FellowshipDto fellowshipDto = FellowshipDto.createFellowshipDto(new HouseDto(exampleHouseEntity1), new HouseDto(exampleHouseEntity2), LocalDate.now());
        FellowshipEntity fellowshipEntity = FellowshipEntity.createFellowshipEntity(exampleHouseEntity1, exampleHouseEntity2, LocalDate.now());
        fellowshipEntity.setFellowshipId(0);
        BDDMockito.given(houseRepository.findById(exampleHouseEntity1.getHouseId()))
                .willReturn(Optional.of(exampleHouseEntity1));
        BDDMockito.given(houseRepository.findById(exampleHouseEntity2.getHouseId()))
                .willReturn(Optional.of(exampleHouseEntity2));
        BDDMockito.given(fellowshipRepository.save(fellowshipEntity))
                .willReturn(fellowshipEntity);
        // WHEN
        underTest.saveFellowship(fellowshipDto);
        // THEN
        BDDMockito.verify(houseRepository).findById(exampleHouseEntity1.getHouseId());
        BDDMockito.verify(houseRepository).findById(exampleHouseEntity2.getHouseId());
        BDDMockito.verify(fellowshipRepository).save(fellowshipEntity);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testUpdateFellowshipWhenInputIsNull() {
        // GIVEN
        FellowshipDto fellowshipDto = null;
        // WHEN
        underTest.updateFellowship(fellowshipDto);
        // THEN
        Assert.fail("IllegalArgumentException should be thrown!");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testUpdateFellowshipWhenFellowshipCannotBeFoundInDb() {
        // GIVEN
        HouseEntity exampleHouseEntity1 = HouseEntity.createHouseEntity("house1", "slogan1");
        exampleHouseEntity1.setHouseId(1);
        HouseEntity exampleHouseEntity2 = HouseEntity.createHouseEntity("house2", "slogan2");
        exampleHouseEntity2.setHouseId(2);
        FellowshipDto fellowshipDto = FellowshipDto.createFellowshipDto(new HouseDto(exampleHouseEntity1), new HouseDto(exampleHouseEntity2), LocalDate.now());
        FellowshipEntity fellowshipEntity = FellowshipEntity.createFellowshipEntity(exampleHouseEntity1, exampleHouseEntity2, LocalDate.now());
        fellowshipEntity.setFellowshipId(0);
        BDDMockito.given(fellowshipRepository.findById(fellowshipEntity.getFellowshipId()))
                .willReturn(Optional.empty());
        // WHEN
        underTest.updateFellowship(fellowshipDto);
        // THEN
        Assert.fail("IllegalArgumentException should be thrown!");
    }

    @Test
    public void testUpdateFellowshipWhenFellowshipCanBeFound() {
        // GIVEN
        HouseEntity exampleHouseEntity1 = HouseEntity.createHouseEntity("house1", "slogan1");
        exampleHouseEntity1.setHouseId(1);
        HouseEntity exampleHouseEntity2 = HouseEntity.createHouseEntity("house2", "slogan2");
        exampleHouseEntity2.setHouseId(2);
        FellowshipDto fellowshipDto = FellowshipDto.createFellowshipDto(new HouseDto(exampleHouseEntity1), new HouseDto(exampleHouseEntity2), LocalDate.now());
        fellowshipDto.setEnd(LocalDate.now());
        FellowshipEntity fellowshipEntity = FellowshipEntity.createFellowshipEntity(exampleHouseEntity1, exampleHouseEntity2, LocalDate.now());
        fellowshipEntity.setFellowshipId(0);
        fellowshipEntity.setEnd(Date.valueOf(LocalDate.now()));
        BDDMockito.given(fellowshipRepository.findById(fellowshipEntity.getFellowshipId()))
                .willReturn(Optional.of(fellowshipEntity));
        BDDMockito.doNothing().when(entityManager)
                .detach(BDDMockito.isA(FellowshipEntity.class));
        BDDMockito.given(fellowshipRepository.save(fellowshipEntity)).willReturn(fellowshipEntity);
        // WHEN
        underTest.updateFellowship(fellowshipDto);
        // THEN
        BDDMockito.verify(fellowshipRepository).findById(fellowshipEntity.getFellowshipId());
        BDDMockito.verify(entityManager).detach(fellowshipEntity);
        BDDMockito.verify(fellowshipRepository).save(fellowshipEntity);
    }
}
