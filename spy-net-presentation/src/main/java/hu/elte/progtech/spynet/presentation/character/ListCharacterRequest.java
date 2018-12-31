package hu.elte.progtech.spynet.presentation.character;

/**
 * It is a simple data holder class, which responsibility to filter characterList by the name parameter.
 */
public class ListCharacterRequest {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
