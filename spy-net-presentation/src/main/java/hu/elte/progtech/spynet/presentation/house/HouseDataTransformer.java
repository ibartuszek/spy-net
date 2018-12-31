package hu.elte.progtech.spynet.presentation.house;

import hu.elte.progtech.spynet.domain.house.House;
import hu.elte.progtech.spynet.presentation.house.create.HouseRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
/**
 * The class is a singleton class of which responsibility is to transform between:
 * house to houseData, houseData to house or houseRequest to house.
 */
@Component
public class HouseDataTransformer {

    public HouseData transformToHouseData(House house) {
        HouseData houseData = null;
        if (house != null) {
            houseData = new HouseData();
            houseData.setHouseId(house.getHouseId());
            houseData.setName(house.getName());
            houseData.setSlogan(house.getSlogan());
            if (house.getCrest() != null) {
                houseData.setCrest(house.getCrest());
                houseData.setCrestUrl(String.format("%s?houseId=%d",
                        HouseDataCrestController.REQUEST_MAPPING, house.getHouseId()));
                houseData.setHasCrest(true);
            } else {
                houseData.setHasCrest(false);
            }
        }
        return houseData;
    }

    public House transformFromHouseData(HouseData houseData) {
        House house = null;
        if (houseData != null) {
            house = House.createHouse(houseData.getName(), houseData.getSlogan());
            house.setHouseId(houseData.getHouseId());
            house.setCrest(houseData.getCrest());
        }
        return house;
    }

    public House transformFromHouseRequest(HouseRequest houseRequest) {
        House house = null;
        if (houseRequest != null) {
            house = new House();
            house.setName(houseRequest.getName());
            house.setSlogan(houseRequest.getSlogan());
            if (!houseRequest.getCrest().isEmpty()) {
                try {
                    house.setCrest(houseRequest.getCrest().getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        }
        return house;
    }

}
