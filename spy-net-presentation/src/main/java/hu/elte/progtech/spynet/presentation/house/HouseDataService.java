package hu.elte.progtech.spynet.presentation.house;

import hu.elte.progtech.spynet.domain.house.House;
import hu.elte.progtech.spynet.domain.house.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HouseDataService {

    @Autowired
    private HouseService houseService;

    @Autowired
    private HouseDataTransformer houseDataTransformer;

    public List<HouseData> getHouseDataList() {
        return transformToHouseDataList(houseService.listHouses());
    }

    private List<HouseData> transformToHouseDataList(List<House> houseList) {
        List<HouseData> houseDataList = new ArrayList<>();
        for (House house : houseList) {
            houseDataList.add(houseDataTransformer.transformToHouseData(house));
        }
        return houseDataList;
    }

    public HouseData getHouseDataByName(String name) {
        return houseDataTransformer.transformToHouseData(houseService.getHouseByName(name));
    }
}
