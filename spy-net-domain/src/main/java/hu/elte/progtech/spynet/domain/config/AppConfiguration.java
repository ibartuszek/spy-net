package hu.elte.progtech.spynet.domain.config;

import hu.elte.progtech.spynet.dal.config.DataBaseConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * The aim of the configuration class is to initialize the singleton Objects.
 */
@Configuration
@Import({DataBaseConfiguration.class})
@ComponentScan("hu.elte.progtech.spynet.domain")
public class AppConfiguration {

    @PostConstruct
    public void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

}
