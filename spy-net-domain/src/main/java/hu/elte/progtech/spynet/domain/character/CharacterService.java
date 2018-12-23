package hu.elte.progtech.spynet.domain.character;

import java.util.List;


public interface CharacterService {

    List<Character> listCharacters();
    List<Character> listCharactersByName(String name);
    void saveCharacter(Character character);
    void updateCharacter(Character character);

}
