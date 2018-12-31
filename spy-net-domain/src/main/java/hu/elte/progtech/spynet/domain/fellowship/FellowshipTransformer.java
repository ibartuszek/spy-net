package hu.elte.progtech.spynet.domain.fellowship;

import hu.elte.progtech.spynet.dal.fellowship.FellowshipDto;
import hu.elte.progtech.spynet.dal.house.HouseDto;
import hu.elte.progtech.spynet.domain.house.House;
import hu.elte.progtech.spynet.domain.house.HouseTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * The class is a singleton class of which responsibility is to transform fellowship to fellowshipDto
 * or fellowshipDto to fellowship.
 */
@Component
public class FellowshipTransformer {

    @Autowired
    private HouseTransformer houseTransformer;

    public FellowshipDto transformToFellowshipDto(Fellowship fellowship) {
        FellowshipDto fellowshipDto = null;
        House house1 = fellowship.getHouse1();
        House house2 = fellowship.getHouse2();
        LocalDate begin = fellowship.getBegin();
        if (house1 != null && house2 != null && begin != null) {
            fellowshipDto = new FellowshipDto();
            fellowshipDto.setFellowshipId(fellowship.getFellowshipId());
            fellowshipDto.setHouse1(houseTransformer.transformToHouseDto(house1));
            fellowshipDto.setHouse2(houseTransformer.transformToHouseDto(house2));
            fellowshipDto.setBegin(begin);
            fellowshipDto.setEnd(fellowship.getEnd());
        }
        return fellowshipDto;
    }

    public Fellowship transformFromFellowshipDto(FellowshipDto fellowshipDto) {
        Fellowship fellowship = null;
        HouseDto houseDto1 = fellowshipDto.getHouse1();
        HouseDto houseDto2 = fellowshipDto.getHouse2();
        LocalDate begin = fellowshipDto.getBegin();
        if (houseDto1 != null && houseDto2 != null && begin != null) {
            fellowship = new Fellowship();
            fellowship.setFellowshipId(fellowshipDto.getFellowshipId());
            fellowship.setHouse1(houseTransformer.transformFromHouseDto(houseDto1));
            fellowship.setHouse2(houseTransformer.transformFromHouseDto(houseDto2));
            fellowship.setBegin(begin);
            fellowship.setEnd(fellowshipDto.getEnd());
        }
        return fellowship;
    }
}
