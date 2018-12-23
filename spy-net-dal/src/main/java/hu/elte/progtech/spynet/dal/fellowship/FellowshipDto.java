package hu.elte.progtech.spynet.dal.fellowship;

import hu.elte.progtech.spynet.dal.house.HouseDto;

import java.time.LocalDate;

public class FellowshipDto {

    private long fellowshipId;
    private HouseDto houese1;
    private HouseDto houese2;
    private LocalDate begin;
    private LocalDate end;

    public FellowshipDto() {}

    public FellowshipDto(FellowshipEntity fellowshipEntity, HouseDto house1, HouseDto house2) {
        this.fellowshipId = fellowshipEntity.getFellowshipId();
        this.houese1 = house1;
        this.houese2 = house2;
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

    public HouseDto getHouese1() {
        return houese1;
    }

    public void setHouese1(HouseDto houese1) {
        this.houese1 = houese1;
    }

    public HouseDto getHouese2() {
        return houese2;
    }

    public void setHouese2(HouseDto houese2) {
        this.houese2 = houese2;
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
                ", houese1=" + houese1 +
                ", houese2=" + houese2 +
                ", begin=" + begin +
                ", end=" + end +
                '}';
    }
}
