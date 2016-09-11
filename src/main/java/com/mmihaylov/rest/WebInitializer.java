package com.mmihaylov.rest;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] {WebConfig.class};
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] {BaseConfiguration.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
}
