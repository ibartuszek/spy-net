package hu.elte.progtech.spynet.presentation.character.create;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CharacterRequest {

    @NotNull
    @Size(min = 5, max = 20, message = "Name must be between 5 and 20 characters.")
    private String name;
    @Min(value = 0, message = "Size of the army cannot be less than zero.")
    private int armySize;
    private String status;
    private String house;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CharacterRequest that = (CharacterRequest) o;

        if (armySize != that.armySize) return false;
        if (!name.equals(that.name)) return false;
        if (!status.equals(that.status)) return false;
        return house.equals(that.house);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + armySize;
        result = 31 * result + status.hashCode();
        result = 31 * result + house.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CharacterRequest{" +
                "name='" + name + '\'' +
                ", armySize=" + armySize +
                ", status='" + status + '\'' +
                ", house='" + house + '\'' +
                '}';
    }
}
