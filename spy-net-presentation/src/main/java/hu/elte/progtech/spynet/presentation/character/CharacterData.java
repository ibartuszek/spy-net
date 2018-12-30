package hu.elte.progtech.spynet.presentation.character;

public class CharacterData {

    private long characterId;
    private String name;
    private int armySize;
    private String status;
    private String house;
    private String characterUrl;

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

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getCharacterUrl() {
        return characterUrl;
    }

    public void setCharacterUrl(String characterUrl) {
        this.characterUrl = characterUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CharacterData that = (CharacterData) o;

        return characterId == that.characterId;
    }

    @Override
    public int hashCode() {
        return (int) (characterId ^ (characterId >>> 32));
    }

    @Override
    public String toString() {
        return "CharacterData{" +
                "characterId=" + characterId +
                ", name='" + name + '\'' +
                ", armySize=" + armySize +
                ", status='" + status + '\'' +
                ", house='" + house + '\'' +
                ", characterUrl='" + characterUrl + '\'' +
                '}';
    }
}
