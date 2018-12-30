package hu.elte.progtech.spynet.presentation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "hu.elte.progtech.spynet.presentation.*")
public class MvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**")
                .addResourceLocations("/WEB-INF/assets/");
    }
    
    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setPrefix("/WEB-INF/views/");
        internalResourceViewResolver.setSuffix(".jsp");
        return internalResourceViewResolver;
    }

    @Bean
    public String[] status() {
        return new String[]{"alive", "dead"};
    }

    @Bean
    public List<String> houseNameList() {
        List<String> houseNameList = new ArrayList<>();
        houseNameList.add("");
        return houseNameList;
    }

    @Bean
    public DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

}
