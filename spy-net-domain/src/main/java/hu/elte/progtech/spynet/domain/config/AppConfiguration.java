package hu.elte.progtech.spynet.domain.config;

import hu.elte.progtech.spynet.dal.config.DataBaseConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * The aim of the configuration class is to initialize the common Objects
 * for the application: wagerDate and UserInterface.
 */
@Configuration
@Import({DataBaseConfiguration.class})
// @PropertySource(value = {"classpath:/properties/wager.properties"}, encoding = "UTF-8")
@ComponentScan(basePackages = {"hu.elte.progtech.spynet.domain"})
public class AppConfiguration {

    @PostConstruct
    public void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

}
