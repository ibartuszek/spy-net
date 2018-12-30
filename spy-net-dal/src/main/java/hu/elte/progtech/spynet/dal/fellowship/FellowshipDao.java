package hu.elte.progtech.spynet.dal.fellowship;

import com.google.common.base.Preconditions;
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
        checkFellowshipDto(fellowshipDto);
        HouseEntity houseEntity1 = getHouseEntity(fellowshipDto.getHouse1());
        HouseEntity houseEntity2 = getHouseEntity(fellowshipDto.getHouse2());
        if (houseEntity1 != null && houseEntity2 != null) {
            fellowshipRepository.save(new FellowshipEntity(fellowshipDto, houseEntity1, houseEntity2));
        } else {
            throwIllegalArgumentException(houseEntity1, houseEntity2);
        }
    }

    private void checkFellowshipDto(FellowshipDto fellowshipDto) {
        Preconditions.checkArgument(fellowshipDto != null, "fellowshipDto cannot be null!");
        Preconditions.checkArgument(fellowshipDto.getHouse1() != null, "fellowshipDto.house1 cannot be null!");
        Preconditions.checkArgument(fellowshipDto.getHouse2() != null, "fellowshipDto.house2 cannot be null!");
        Preconditions.checkArgument(fellowshipDto.getBegin() != null, "fellowshipDto.begin cannot be null!");
    }

    private void throwIllegalArgumentException(HouseEntity houseEntity1, HouseEntity houseEntity2) {
        String msg = "";
        if (houseEntity1 == null) {
            msg = msg + "HouseEntity1 cannot be found in DB.";
        }
        if (houseEntity2 == null) {
            msg = msg + " HouseEntity2 cannot be found in DB.";
        }
        throw new IllegalArgumentException(msg);
    }

    @Transactional
    public void updateFellowship(FellowshipDto fellowshipDto) {
        checkFellowshipDtoWithEnd(fellowshipDto);
        FellowshipEntity fellowshipEntity;
        Optional<FellowshipEntity> optionalFellowshipEntity = fellowshipRepository.findById(fellowshipDto.getFellowshipId());
        if (optionalFellowshipEntity.isPresent()) {
            fellowshipEntity = optionalFellowshipEntity.get();
            modifyFellowshipEntity(fellowshipEntity, fellowshipDto);
        } else {
            throw new IllegalArgumentException("FellowshipEntity cannot be found in db with the following id: "
                    + fellowshipDto.getFellowshipId());
        }
    }

    private void checkFellowshipDtoWithEnd(FellowshipDto fellowshipDto) {
        checkFellowshipDto(fellowshipDto);
        Preconditions.checkArgument(fellowshipDto.getEnd() != null, "fellowshipDto.end cannot be null!");
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

    public FellowshipDto findById(long fellowshipId) {
        Preconditions.checkArgument(fellowshipId != 0, "fellowshipId cannot be 0!");
        Optional<FellowshipEntity> optionalFellowshipEntity = fellowshipRepository.findById(fellowshipId);
        FellowshipDto fellowshipDto = null;
        if (optionalFellowshipEntity.isPresent()) {
            FellowshipEntity fellowshipEntity = optionalFellowshipEntity.get();
            fellowshipDto = new FellowshipDto(fellowshipEntity,
                    new HouseDto(fellowshipEntity.getHouse1()), new HouseDto(fellowshipEntity.getHouse2()));
        }
        return fellowshipDto;
    }
}
