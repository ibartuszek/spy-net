package hu.elte.progtech.spynet.fellowship;

import hu.elte.progtech.spynet.dal.fellowship.FellowshipDto;
import hu.elte.progtech.spynet.dal.house.HouseDto;
import hu.elte.progtech.spynet.domain.fellowship.Fellowship;
import hu.elte.progtech.spynet.house.HouseUtil;

public class FellowshipUtil {

    public static FellowshipDto createFellowshipDto(Fellowship fellowship) {
        FellowshipDto fellowshipDto = new FellowshipDto();
        fellowshipDto.setBegin(fellowship.getBegin());
        HouseDto houseDto1 = HouseUtil.createHouseDto(fellowship.getHouese1());
        HouseDto houseDto2 = HouseUtil.createHouseDto(fellowship.getHouese2());
        fellowshipDto.setHouse1(houseDto1);
        fellowshipDto.setHouse2(houseDto2);
        fellowshipDto.setFellowshipId(fellowship.getFellowshipId());
        fellowshipDto.setEnd(fellowship.getEnd());
        return fellowshipDto;
    }
}
