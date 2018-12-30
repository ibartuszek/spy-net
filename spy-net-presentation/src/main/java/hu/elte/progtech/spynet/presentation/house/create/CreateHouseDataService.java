package hu.elte.progtech.spynet.presentation.house.create;

import com.google.common.base.Preconditions;
import hu.elte.progtech.spynet.domain.house.HouseService;
import hu.elte.progtech.spynet.presentation.house.HouseData;
import hu.elte.progtech.spynet.presentation.house.HouseDataService;
import hu.elte.progtech.spynet.presentation.house.HouseDataTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CreateHouseDataService {

    @Autowired
    private HouseService houseService;

    @Autowired
    private HouseDataTransformer houseDataTransformer;

    @Autowired
    private HouseDataService houseDataService;

    public boolean saveHouse(HouseRequest houseRequest) {
        Preconditions.checkArgument(houseRequest != null, "houseRequest cannot be null!");
        boolean notFound = checkHouse(houseRequest);
        if (notFound) {
            houseService.saveHouse(houseDataTransformer.transformFromHouseRequest(houseRequest));
        }
        return notFound;
    }

    private boolean checkHouse(HouseRequest houseRequest) {
        List<HouseData> houseDataList = houseDataService.getHouseDataList();
        boolean found = false;
        int index = 0;
        while (index < houseDataList.size() && !found) {
            found = houseRequest.getName().equals(houseDataList.get(index).getName());
            index++;
        }
        return !found;
    }

}
