package hu.elte.progtech.spynet.presentation.house;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HouseNameListService {

    @Autowired
    private List<String> houseNameList;

    @Autowired
    private HouseDataService houseDataService;

    public List<String> getHouseNameList() {
        updateHouseNameList();
        return houseNameList;
    }

    private void updateHouseNameList() {
        houseNameList.clear();
        houseNameList.add("");
        List<HouseData> houseDataList = houseDataService.getHouseDataList();
        for (HouseData houseData : houseDataList) {
            houseNameList.add(houseData.getName());
        }
    }
}
