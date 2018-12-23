package hu.elte.progtech.spynet.dal.fellowship;

import hu.elte.progtech.spynet.dal.house.HouseEntity;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name="fellowships")
public class FellowshipEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long fellowshipId;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    private HouseEntity house1;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    private HouseEntity house2;
    @Column(length = 20, nullable = false)
    private Date begin;
    @Column(length = 20)
    private Date end;
    @Version
    private long version;

    public FellowshipEntity() {}

    public FellowshipEntity(FellowshipDto fellowshipDto, HouseEntity house1, HouseEntity house2) {
        this.house1 = house1;
        this.house2 = house2;
        this.begin = Date.valueOf(fellowshipDto.getBegin());
        LocalDate tempEnd = fellowshipDto.getEnd();
        if (tempEnd != null) {
            this.end = Date.valueOf(tempEnd);
        }
    }

    public long getFellowshipId() {
        return fellowshipId;
    }

    public void setFellowshipId(long fellowshipId) {
        this.fellowshipId = fellowshipId;
    }

    public HouseEntity getHouse1() {
        return house1;
    }

    public void setHouse1(HouseEntity house1) {
        this.house1 = house1;
    }

    public HouseEntity getHouse2() {
        return house2;
    }

    public void setHouse2(HouseEntity house2) {
        this.house2 = house2;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FellowshipEntity that = (FellowshipEntity) o;

        return fellowshipId == that.fellowshipId;
    }

    @Override
    public int hashCode() {
        return (int) (fellowshipId ^ (fellowshipId >>> 32));
    }

    @Override
    public String toString() {
        return "FellowshipEntity{" +
                "fellowshipId=" + fellowshipId +
                ", house1=" + house1 +
                ", house2=" + house2 +
                ", begin=" + begin +
                ", end=" + end +
                ", version=" + version +
                '}';
    }
}
