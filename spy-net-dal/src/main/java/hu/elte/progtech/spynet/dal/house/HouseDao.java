package hu.elte.progtech.spynet.dal.house;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HouseDao {


    @Autowired
    private HouseRepository houseRepository;

    public void saveHouse(HouseDto houseDto) {
        Preconditions.checkArgument(houseDto != null, "houseDto cannot be null!");
        houseRepository.save(new HouseEntity(houseDto));
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
