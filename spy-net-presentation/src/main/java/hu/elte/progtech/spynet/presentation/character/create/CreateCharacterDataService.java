package hu.elte.progtech.spynet.presentation.character.create;

import com.google.common.base.Preconditions;
import hu.elte.progtech.spynet.domain.character.CharacterService;
import hu.elte.progtech.spynet.presentation.character.CharacterData;
import hu.elte.progtech.spynet.presentation.character.CharacterDataService;
import hu.elte.progtech.spynet.presentation.character.CharacterDataTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CreateCharacterDataService {

    @Autowired
    private CharacterService characterService;

    @Autowired
    private CharacterDataTransformer characterDataTransformer;

    @Autowired
    private CharacterDataService characterDataService;

    public boolean saveCharacter(CharacterRequest characterRequest) {
        Preconditions.checkArgument(characterRequest != null, "characterRequest cannot be null!");
        boolean notFound = checkCharacter(characterRequest);
        if (notFound) {
            characterService.saveCharacter(characterDataTransformer.transformFromCharacterRequest(characterRequest));
        }
        return notFound;
    }

    private boolean checkCharacter(CharacterRequest characterRequest) {
        List<CharacterData> characterDataList = characterDataService.getCharacterDataList();
        boolean found = false;
        int index = 0;
        while (index < characterDataList.size() && !found) {
            found = characterRequest.getName().equals(characterDataList.get(index++).getName());
        }
        return !found;
    }
}
