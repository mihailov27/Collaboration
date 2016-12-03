package com.mmihaylov.rest;

import com.mmihaylov.rest.data.configuration.DataConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by mmihaylov on 8/21/16.
 */
@Configuration
@PropertySource("classpath:collaboration.properties")
@ComponentScan(basePackages = {"com.mmihaylov.rest.services", "com.mmihaylov.rest.utils"})
@Import(value = {DataConfiguration.class})
public class BaseConfiguration {

}
