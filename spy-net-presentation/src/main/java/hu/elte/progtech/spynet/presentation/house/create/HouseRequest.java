package hu.elte.progtech.spynet.presentation.house.create;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class HouseRequest {

    @NotNull
    @Size(min = 5, max = 20, message = "Name must be between 5 and 20 characters.")
    private String name;
    @NotNull
    @Size(min = 5, max = 40, message = "Slogan must be between 5 and 40 characters.")
    private String slogan;
    private MultipartFile crest;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public MultipartFile getCrest() {
        return crest;
    }

    public void setCrest(MultipartFile crest) {
        this.crest = crest;
    }

    @Override
    public String toString() {
        return "HouseRequest{" +
                "name='" + name + '\'' +
                ", slogan='" + slogan + '\'' +
                '}';
    }
}
