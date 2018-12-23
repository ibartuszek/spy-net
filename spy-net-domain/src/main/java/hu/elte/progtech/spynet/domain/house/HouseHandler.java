package hu.elte.progtech.spynet.domain.house;

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
        houseDao.saveHouse(houseTransformer.transformToHouseDto(house));
    }

    @Override
    public List<House> listHouses() {
        return transformToHouseList(houseDao.listHouses());
    }

    private List<House> transformToHouseList(List<HouseDto> houseDtoList) {
        List<House> houseList = new ArrayList<>();
        for (HouseDto houseDto : houseDtoList) {
            houseList.add(houseTransformer.transformFromHouseDto(houseDto));
        }
        return houseList;
    }
}
