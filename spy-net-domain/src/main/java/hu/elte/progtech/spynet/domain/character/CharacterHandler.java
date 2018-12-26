package hu.elte.progtech.spynet.domain.character;

import com.google.common.base.Preconditions;
import hu.elte.progtech.spynet.dal.character.CharacterDao;
import hu.elte.progtech.spynet.dal.character.CharacterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("characterService")
public class CharacterHandler implements CharacterService {

    @Autowired
    private CharacterDao characterDao;

    @Autowired
    private CharacterTransformer characterTransformer;

    @Override
    public List<Character> listCharacters() {
        return transformToCharacterList(characterDao.listCharacters());
    }

    private List<Character> transformToCharacterList(List<CharacterDto> characterDtoList) {
        List<Character> characterList = new ArrayList<>();
        for (CharacterDto characterDto : characterDtoList) {
            characterList.add(characterTransformer.transformFromCharacterDto(characterDto));
        }
        return characterList;
    }

    @Override
    public List<Character> listCharactersByName(String name) {
        Preconditions.checkArgument(name != null, "name cannot be null!");
        List<Character> filteredCharacterList = new ArrayList<>();
        for (Character character : listCharacters()) {
            if (character.getName().contains(name)) {
                filteredCharacterList.add(character);
            }
        }
        return filteredCharacterList;
    }

    public void saveCharacter(Character character) {
        Preconditions.checkArgument(character != null, "character cannot be null!");
        characterDao.saveCharacter(characterTransformer.transformToCharacterDto(character));
    }

    @Override
    public void updateCharacter(Character character) {
        Preconditions.checkArgument(character != null, "character cannot be null!");
        if (character.getStatus().equals(CharacterStatus.DEAD)) {
            character.setArmySize(0);
        }
        characterDao.updateCharacter(characterTransformer.transformToCharacterDto(character));
    }
}
