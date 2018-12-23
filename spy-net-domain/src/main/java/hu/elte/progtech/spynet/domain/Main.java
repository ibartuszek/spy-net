package hu.elte.progtech.spynet.domain;

import hu.elte.progtech.spynet.domain.character.Character;
import hu.elte.progtech.spynet.domain.character.CharacterService;
import hu.elte.progtech.spynet.domain.config.AppConfiguration;
import hu.elte.progtech.spynet.domain.fellowship.Fellowship;
import hu.elte.progtech.spynet.domain.fellowship.FellowshipService;
import hu.elte.progtech.spynet.domain.house.House;
import hu.elte.progtech.spynet.domain.house.HouseService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.register(AppConfiguration.class);
            context.refresh();
            CharacterService characterService = context.getBean(CharacterService.class);
            HouseService houseService = context.getBean(HouseService.class);
            FellowshipService fellowshipService = context.getBean(FellowshipService.class);

            System.out.println("\nTest START: ...");
            House dothraki = House.createHouse("Dothraki", "ATAAACK!");
            System.out.println(dothraki.toString());
            houseService.saveHouse(dothraki);
            Character khalDrogo = Character.createCharacter("Khal Drogo", 10000,  null);
            System.out.println("Khal Drogo: " + khalDrogo.toString());
            characterService.saveCharacter(khalDrogo);
            dothraki = houseService.listHouses().get(0);
            System.out.println(dothraki);
            khalDrogo = characterService.listCharacters().get(0);
            System.out.println(khalDrogo);
            khalDrogo.setHouse(dothraki);
            System.out.println("Khal Drogo: " + khalDrogo.toString());
            characterService.updateCharacter(khalDrogo);
            System.out.println("Test END\n");
            List<Character> characters = characterService.listCharactersByName("Dr");
            System.out.println("characters size:" + characters.size());
            List<Character> emptyList = characterService.listCharactersByName("q");
            System.out.println("emptyList size: " + emptyList.size());
            House targaryan = House.createHouse("Targaryan", "Fire and blood");
            houseService.saveHouse(targaryan);
            targaryan = houseService.listHouses().get(1);
            Character daenerys = Character.createCharacter("Daenerys", 1, targaryan);
            System.out.println(daenerys);
            characterService.saveCharacter(daenerys);
            Fellowship targeryanDothraki = Fellowship.createFellowship(targaryan, dothraki, LocalDate.now());
            System.out.println(targeryanDothraki);
            fellowshipService.saveFellowship(targeryanDothraki);
            targeryanDothraki = fellowshipService.listFellowships().get(0);
            System.out.println(targeryanDothraki);
            targeryanDothraki.setEnd(LocalDate.now());
            fellowshipService.updateFellowship(targeryanDothraki);
            System.out.println(targeryanDothraki);
        }
    }

}
