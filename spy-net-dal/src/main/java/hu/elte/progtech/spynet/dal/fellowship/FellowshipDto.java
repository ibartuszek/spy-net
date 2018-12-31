package hu.elte.progtech.spynet.dal.fellowship;

import hu.elte.progtech.spynet.dal.house.HouseDto;

import java.time.LocalDate;

/**
 * Data transfer object between Data access layer and domain layer.
 */
public class FellowshipDto {

    private long fellowshipId;
    private HouseDto house1;
    private HouseDto house2;
    private LocalDate begin;
    private LocalDate end;

    public FellowshipDto() {}

    /**
     * Create a FellowshipDto from FellowshipEntity.
     * @param fellowshipEntity If it is null it throws a NullPointerException.
     * @param house1
     * @param house2
     */
    public FellowshipDto(FellowshipEntity fellowshipEntity, HouseDto house1, HouseDto house2) {
        this.fellowshipId = fellowshipEntity.getFellowshipId();
        this.house1 = house1;
        this.house2 = house2;
        if (fellowshipEntity.getBegin() != null) {
            this.begin = fellowshipEntity.getBegin().toLocalDate();
        }
        if (fellowshipEntity.getEnd() != null) {
            this.end = fellowshipEntity.getEnd().toLocalDate();
        }
    }

    public long getFellowshipId() {
        return fellowshipId;
    }

    public void setFellowshipId(long fellowshipId) {
        this.fellowshipId = fellowshipId;
    }

    public HouseDto getHouse1() {
        return house1;
    }

    public void setHouse1(HouseDto house1) {
        this.house1 = house1;
    }

    public HouseDto getHouse2() {
        return house2;
    }

    public void setHouse2(HouseDto house2) {
        this.house2 = house2;
    }

    public LocalDate getBegin() {
        return begin;
    }

    public void setBegin(LocalDate begin) {
        this.begin = begin;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FellowshipDto that = (FellowshipDto) o;

        return fellowshipId == that.fellowshipId;
    }

    @Override
    public int hashCode() {
        return (int) (fellowshipId ^ (fellowshipId >>> 32));
    }

    @Override
    public String toString() {
        return "FellowshipDto{" +
                "fellowshipId=" + fellowshipId +
                ", house1=" + house1 +
                ", house2=" + house2 +
                ", begin=" + begin +
                ", end=" + end +
                '}';
    }

    public static FellowshipDto createFellowshipDto(HouseDto house1, HouseDto house2, LocalDate start) {
        FellowshipDto fellowshipDto= new FellowshipDto();
        fellowshipDto.setHouse1(house1);
        fellowshipDto.setHouse2(house2);
        fellowshipDto.setBegin(start);
        return fellowshipDto;
    }
}
