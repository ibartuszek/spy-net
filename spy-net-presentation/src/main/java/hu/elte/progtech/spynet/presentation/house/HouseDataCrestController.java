package hu.elte.progtech.spynet.presentation.house;

import hu.elte.progtech.spynet.domain.house.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
public class HouseDataCrestController {

    public static final String REQUEST_MAPPING = "/crestUrl";

    @Autowired
    private HouseService houseService;

    @RequestMapping(value = REQUEST_MAPPING, method = RequestMethod.GET)
    @ResponseBody
    public AbstractResource downloadCover(HouseData houseData, HttpServletResponse httpServletResponse) {
        return new ByteArrayResource(houseService.getHouseById(houseData.getHouseId()).getCrest());
    }

}
