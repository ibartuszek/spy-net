package hu.elte.progtech.spynet.domain.fellowship;

import hu.elte.progtech.spynet.domain.house.House;

import java.time.LocalDate;

public class Fellowship {

    private long fellowshipId;
    private House house1;
    private House house2;
    private LocalDate begin;
    private LocalDate end;

    public static Fellowship createFellowship(House house1, House house2, LocalDate startDate) {
        Fellowship fellowship = new Fellowship();
        fellowship.setHouse1(house1);
        fellowship.setHouse2(house2);
        fellowship.setBegin(startDate);
        return fellowship;
    }

    public long getFellowshipId() {
        return fellowshipId;
    }

    public void setFellowshipId(long fellowshipId) {
        this.fellowshipId = fellowshipId;
    }

    public House getHouse1() {
        return house1;
    }

    public void setHouse1(House house1) {
        this.house1 = house1;
    }

    public House getHouse2() {
        return house2;
    }

    public void setHouse2(House house2) {
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

        Fellowship that = (Fellowship) o;

        if (!house1.equals(that.house1)) return false;
        if (!house2.equals(that.house2)) return false;
        if (!begin.equals(that.begin)) return false;
        return end != null ? end.equals(that.end) : that.end == null;
    }

    @Override
    public int hashCode() {
        int result = house1.hashCode();
        result = 31 * result + house2.hashCode();
        result = 31 * result + begin.hashCode();
        result = 31 * result + (end != null ? end.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Fellowship{" +
                "fellowshipId=" + fellowshipId +
                ", house1=" + house1 +
                ", house2=" + house2 +
                ", begin=" + begin +
                ", end=" + end +
                '}';
    }
}
