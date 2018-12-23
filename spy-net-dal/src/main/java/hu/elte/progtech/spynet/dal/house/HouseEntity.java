package hu.elte.progtech.spynet.dal.house;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name="houses")
public class HouseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long houseId;
    @Column(length = 20, nullable = false, unique = true)
    private String name;
    @Column(length = 20, nullable = false, unique = true)
    private String slogan;
    @Lob
    private Byte[] crest;
    @Version
    private long version;

    public HouseEntity() {}

    public HouseEntity(HouseDto houseDto) {
        this.name = houseDto.getName();
        this.slogan = houseDto.getSlogan();
        this.crest = houseDto.getCrest();
    }

    public long getHouseId() {
        return houseId;
    }

    public void setHouseId(long houseId) {
        this.houseId = houseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public Byte[] getCrest() {
        return crest;
    }

    public void setCrest(Byte[] crest) {
        this.crest = crest;
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

        HouseEntity that = (HouseEntity) o;

        return houseId == that.houseId;
    }

    @Override
    public int hashCode() {
        return (int) (houseId ^ (houseId >>> 32));
    }

    @Override
    public String toString() {
        return "HouseEntity{" +
                "houseId=" + houseId +
                ", name='" + name + '\'' +
                ", slogan='" + slogan + '\'' +
                ", crest=" + Arrays.toString(crest) +
                ", version=" + version +
                '}';
    }
}
