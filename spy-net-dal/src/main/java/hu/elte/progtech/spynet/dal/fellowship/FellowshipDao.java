package hu.elte.progtech.spynet.dal.fellowship;

import hu.elte.progtech.spynet.dal.house.HouseDto;
import hu.elte.progtech.spynet.dal.house.HouseEntity;
import hu.elte.progtech.spynet.dal.house.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class FellowshipDao {

    @Autowired
    private FellowshipRepository fellowshipRepository;

    @Autowired
    private HouseRepository houseRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<FellowshipDto> listFellowships(){
        List<FellowshipEntity> fellowshipEntityList = (List<FellowshipEntity>) fellowshipRepository.findAll();
        return convertToFellowshipDtoList(fellowshipEntityList);
    }

    private List<FellowshipDto> convertToFellowshipDtoList(List<FellowshipEntity> fellowshipEntityList) {
        List<FellowshipDto> fellowshipDtoList = new ArrayList<>();
        for (FellowshipEntity fellowshipEntity : fellowshipEntityList) {
            fellowshipDtoList.add(new FellowshipDto(
                    fellowshipEntity,
                    new HouseDto(fellowshipEntity.getHouse1()),
                    new HouseDto(fellowshipEntity.getHouse2())));
        }
        return fellowshipDtoList;
    }

    @Transactional
    public void saveFellowship(FellowshipDto fellowshipDto) {
        HouseEntity houseEntity1 = getHouseEntity(fellowshipDto.getHouese1());
        HouseEntity houseEntity2 = getHouseEntity(fellowshipDto.getHouese2());
        if (houseEntity1 != null && houseEntity2 != null) {
            fellowshipRepository.save(new FellowshipEntity(fellowshipDto, houseEntity1, houseEntity2));
        }
    }

    @Transactional
    public void updateFellowship(FellowshipDto fellowshipDto) {
        FellowshipEntity fellowshipEntity;
        Optional<FellowshipEntity> optionalFellowshipEntity = fellowshipRepository.findById(fellowshipDto.getFellowshipId());
        if (optionalFellowshipEntity.isPresent()) {
            fellowshipEntity = optionalFellowshipEntity.get();
            modifyFellowshipEntity(fellowshipEntity, fellowshipDto);
        }
    }

    private HouseEntity getHouseEntity(HouseDto houseDto) {
        HouseEntity houseEntity = null;
        Optional<HouseEntity> optionalHouse = houseRepository.findById(houseDto.getHouseId());
        if (optionalHouse.isPresent()) {
            houseEntity = optionalHouse.get();
        }
        return houseEntity;
    }

    private void modifyFellowshipEntity(FellowshipEntity fellowshipEntity, FellowshipDto fellowshipDto) {
        entityManager.detach(fellowshipEntity);
        fellowshipEntity.setEnd(Date.valueOf(fellowshipDto.getEnd()));
        fellowshipRepository.save(fellowshipEntity);
    }

}
