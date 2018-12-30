package hu.elte.progtech.spynet.dal.house;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public HouseDto findByName(String name) {
        Preconditions.checkArgument(name != null, "name of the house cannot be null!");
        Preconditions.checkArgument(name != "", "name of the house cannot be empty string!");
        Optional<HouseEntity> optionalHouseEntity = houseRepository.findByName(name);
        return createHouseDto(optionalHouseEntity);
    }

    private HouseDto createHouseDto(Optional<HouseEntity> optionalHouseEntity) {
        HouseDto houseDto = null;
        if (optionalHouseEntity.isPresent()) {
            houseDto = new HouseDto(optionalHouseEntity.get());
        }
        return houseDto;
    }

    public HouseDto findById(long id) {
        Preconditions.checkArgument(id != 0, "id of the house cannot be zero!");
        Optional<HouseEntity> optionalHouseEntity = houseRepository.findById(id);
        return createHouseDto(optionalHouseEntity);
    }
}
