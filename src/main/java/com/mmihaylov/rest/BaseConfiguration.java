package com.mmihaylov.rest;

import com.mmihaylov.rest.data.configuration.DataConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

/**
 * Created by mmihaylov on 8/21/16.
 */
@Configuration
@Import(value = {DataConfiguration.class})
@ComponentScan(basePackages = {"com.mmihaylov.rest.services", "com.mmihaylov.rest.utils"})
public class BaseConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }
}
