package hu.elte.progtech.spynet.presentation.fellowship;

/**
 * This class is a pojo class, which responsibility to show a Fellowship by a controller.
 */
public class FellowshipData {

    private long fellowshipId;
    private String house1;
    private String house2;
    private String begin;
    private String end;
    private String fellowshipUrl;

    public long getFellowshipId() {
        return fellowshipId;
    }

    public void setFellowshipId(long fellowshipId) {
        this.fellowshipId = fellowshipId;
    }

    public String getHouse1() {
        return house1;
    }

    public void setHouse1(String house1) {
        this.house1 = house1;
    }

    public String getHouse2() {
        return house2;
    }

    public void setHouse2(String house2) {
        this.house2 = house2;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getFellowshipUrl() {
        return fellowshipUrl;
    }

    public void setFellowshipUrl(String fellowshipUrl) {
        this.fellowshipUrl = fellowshipUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FellowshipData that = (FellowshipData) o;

        if (!house1.equals(that.house1)) return false;
        if (!house2.equals(that.house2)) return false;
        if (!begin.equals(that.begin)) return false;
        if (!end.equals(that.end)) return false;
        return fellowshipUrl.equals(that.fellowshipUrl);
    }

    @Override
    public int hashCode() {
        int result = house1.hashCode();
        result = 31 * result + house2.hashCode();
        result = 31 * result + begin.hashCode();
        result = 31 * result + end.hashCode();
        result = 31 * result + fellowshipUrl.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "FellowshipData{" +
                "fellowshipId=" + fellowshipId +
                ", house1='" + house1 + '\'' +
                ", house2='" + house2 + '\'' +
                ", begin='" + begin + '\'' +
                ", end='" + end + '\'' +
                ", fellowshipUrl='" + fellowshipUrl + '\'' +
                '}';
    }
}
