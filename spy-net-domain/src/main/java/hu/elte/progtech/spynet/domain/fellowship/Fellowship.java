package hu.elte.progtech.spynet.domain.fellowship;

import hu.elte.progtech.spynet.domain.house.House;

import java.time.LocalDate;

public class Fellowship {

    private long fellowshipId;
    private House houese1;
    private House houese2;
    private LocalDate begin;
    private LocalDate end;

    public static Fellowship createFellowship(House house1, House house2, LocalDate startDate) {
        Fellowship fellowship = new Fellowship();
        fellowship.setHouese1(house1);
        fellowship.setHouese2(house2);
        fellowship.setBegin(startDate);
        return fellowship;
    }

    public long getFellowshipId() {
        return fellowshipId;
    }

    public void setFellowshipId(long fellowshipId) {
        this.fellowshipId = fellowshipId;
    }

    public House getHouese1() {
        return houese1;
    }

    public void setHouese1(House houese1) {
        this.houese1 = houese1;
    }

    public House getHouese2() {
        return houese2;
    }

    public void setHouese2(House houese2) {
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

        Fellowship that = (Fellowship) o;

        return fellowshipId == that.fellowshipId;
    }

    @Override
    public int hashCode() {
        return (int) (fellowshipId ^ (fellowshipId >>> 32));
    }

    @Override
    public String toString() {
        return "Fellowship{" +
                "fellowshipId=" + fellowshipId +
                ", houese1=" + houese1 +
                ", houese2=" + houese2 +
                ", begin=" + begin +
                ", end=" + end +
                '}';
    }
}
