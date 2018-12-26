package hu.elte.progtech.spynet.house;

import hu.elte.progtech.spynet.dal.house.HouseDto;
import hu.elte.progtech.spynet.domain.house.House;

public class HouseUtil {

    public static HouseDto createHouseDto(House house) {
        HouseDto houseDto = new HouseDto();
        houseDto.setName(house.getName());
        houseDto.setSlogan(house.getSlogan());
        houseDto.setHouseId(house.getHouseId());
        houseDto.setCrest(house.getCrest());
        return houseDto;
    }
}
