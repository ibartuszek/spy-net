package hu.elte.progtech.spynet.domain.house;

import com.google.common.base.Preconditions;
import hu.elte.progtech.spynet.dal.house.HouseDao;
import hu.elte.progtech.spynet.dal.house.HouseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("houseService")
public class HouseHandler implements HouseService {

    @Autowired
    private HouseDao houseDao;

    @Autowired
    private HouseTransformer houseTransformer;

    @Override
    public void saveHouse(House house) {
        Preconditions.checkArgument(house != null, "house cannot be null!");
        houseDao.saveHouse(houseTransformer.transformToHouseDto(house));
    }

    @Override
    public List<House> listHouses() {
        return transformToHouseList(houseDao.listHouses());
    }

    @Override
    public House getHouseByName(String name) {
        Preconditions.checkArgument(name != null, "name of the house cannot be null!");
        Preconditions.checkArgument(name != "", "name of the house cannot be empty string!");
        return houseTransformer.transformFromHouseDto(houseDao.findByName(name));
    }

    @Override
    public House getHouseById(long id) {
        Preconditions.checkArgument(id != 0, "id of the house cannot be zero!");
        return houseTransformer.transformFromHouseDto(houseDao.findById(id));
    }

    private List<House> transformToHouseList(List<HouseDto> houseDtoList) {
        List<House> houseList = new ArrayList<>();
        for (HouseDto houseDto : houseDtoList) {
            houseList.add(houseTransformer.transformFromHouseDto(houseDto));
        }
        return houseList;
    }
}
