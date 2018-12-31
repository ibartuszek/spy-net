package hu.elte.progtech.spynet.presentation.fellowship.create;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * This class is a data transfer object to create or modify a Fellowship.
 * It is validated, by Spring, Hybernate on form.
 */
public class FellowshipRequest {

    @Size(min = 5, max = 20, message = "Name must be between 5 and 20 characters.")
    private String house1;
    @Size(min = 5, max = 20, message = "Name must be between 5 and 20 characters.")
    private String house2;
    @Pattern(regexp = "^([0-9]{4})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$",
            message = "Date must be in: 'YYYY-HH-DD' format.")
    private String begin;
    @Pattern(regexp = "^(([0-9]{4})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01]))|$",
            message = "Date must be in: 'YYYY-HH-DD' format.")
    private String end;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FellowshipRequest that = (FellowshipRequest) o;

        if (!house1.equals(that.house1)) return false;
        if (!house2.equals(that.house2)) return false;
        if (!begin.equals(that.begin)) return false;
        return end.equals(that.end);
    }

    @Override
    public int hashCode() {
        int result = house1.hashCode();
        result = 31 * result + house2.hashCode();
        result = 31 * result + begin.hashCode();
        result = 31 * result + end.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "FellowshipRequest{" +
                "house1='" + house1 + '\'' +
                ", house2='" + house2 + '\'' +
                ", begin='" + begin + '\'' +
                ", end='" + end + '\'' +
                '}';
    }
}
