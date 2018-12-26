package hu.elte.progtech.spynet.domain.house;

import hu.elte.progtech.spynet.dal.house.HouseDto;
import org.springframework.stereotype.Component;

@Component
public class HouseTransformer {

    public HouseDto transformToHouseDto(House house) {
        HouseDto houseDto = null;
        if (house != null) {
            houseDto = new HouseDto();
            houseDto.setHouseId(house.getHouseId());
            houseDto.setName(house.getName());
            houseDto.setSlogan(house.getSlogan());
            houseDto.setCrest(house.getCrest());
        }
        return houseDto;
    }


    public House transformFromHouseDto(HouseDto houseDto) {
        House house = null;
        if (houseDto != null) {
            house = new House();
            house.setHouseId(houseDto.getHouseId());
            house.setName(houseDto.getName());
            house.setSlogan(houseDto.getSlogan());
            house.setCrest(houseDto.getCrest());
        }
        return house;
    }
}
