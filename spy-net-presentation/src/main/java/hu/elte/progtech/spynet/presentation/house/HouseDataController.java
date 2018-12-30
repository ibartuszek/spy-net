package hu.elte.progtech.spynet.presentation.house;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HouseDataController {

    @Autowired
    private HouseDataService houseDataService;

    public static final String REQUEST_MAPPING = "/houses";

    @ModelAttribute("houseDataList")
    public List<HouseData> houseDataList() {
        return houseDataService.getHouseDataList();
    }

    @RequestMapping(value = {REQUEST_MAPPING})
    public String houses() {
        return "houses";
    }
}
