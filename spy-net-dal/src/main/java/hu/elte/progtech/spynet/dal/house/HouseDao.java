package hu.elte.progtech.spynet.dal.house;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class HouseDao {


    @Autowired
    private HouseRepository houseRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public void saveHouse(HouseDto house) {
        houseRepository.save(new HouseEntity(house));
    }

    public List<HouseDto> listHouses() {
        List<HouseEntity> houseEntityList = (List<HouseEntity>)houseRepository.findAll();
        return convertToHouseDtoList(houseEntityList);
    }

    private List<HouseDto> convertToHouseDtoList(List<HouseEntity> houseEntityList) {
        List<HouseDto> houseDtoList = new ArrayList<>();
        for (HouseEntity houseEntity : houseEntityList) {
            houseDtoList.add(new HouseDto(houseEntity));
        }
        return houseDtoList;
    }
}
