package com.mmihaylov.rest.utils;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class PropertyUtilsImpl implements PropertyUtils {

    private Environment environment;

    public PropertyUtilsImpl(Environment environment) {
        this.environment = environment;
    }

    protected String getProperty(String key) {
        return environment.getProperty(key);
    }

    protected Integer getIntProperty(String key) {
        return environment.getProperty(key, Integer.class);
    }

    @Override
    public String getProfileImageDirectory() {
        return getProperty(KEYS.PROFILE_IMAGE_DIRECTORY);
    }

    private interface KEYS {
        String PROFILE_IMAGE_DIRECTORY = "profile.images.directory";
    }
}
