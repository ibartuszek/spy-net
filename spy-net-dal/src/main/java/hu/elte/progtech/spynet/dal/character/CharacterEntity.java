package hu.elte.progtech.spynet.dal.character;

import hu.elte.progtech.spynet.dal.house.HouseEntity;

import javax.persistence.*;

@Entity
@Table(name="characters")
public class CharacterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long characterId;
    @Column(length = 30, nullable = false, unique = true)
    private String name;
    @Column(length = 20)
    private int armySize;
    @Column(length = 10, nullable = false)
    private String status;
    @OneToOne(cascade = CascadeType.ALL)
    private HouseEntity house;
    @Version
    private long version;

    public CharacterEntity(){}

    public CharacterEntity(CharacterDto characterDto, HouseEntity houseEntity) {
        this.name = characterDto.getName();
        this.armySize = characterDto.getArmySize();
        this.status = characterDto.getStatus();
        this.house = houseEntity;
    }

    public long getCharacterId() {
        return characterId;
    }

    public void setCharacterId(long characterId) {
        this.characterId = characterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArmySize() {
        return armySize;
    }

    public void setArmySize(int armySize) {
        this.armySize = armySize;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HouseEntity getHouse() {
        return house;
    }

    public void setHouse(HouseEntity house) {
        this.house = house;
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

        CharacterEntity that = (CharacterEntity) o;

        return characterId == that.characterId;
    }

    @Override
    public int hashCode() {
        return (int) (characterId ^ (characterId >>> 32));
    }

    @Override
    public String toString() {
        return "CharacterEntity{" +
                "characterId=" + characterId +
                ", name='" + name + '\'' +
                ", armySize=" + armySize +
                ", status='" + status + '\'' +
                ", house=" + house +
                ", version=" + version +
                '}';
    }
}
