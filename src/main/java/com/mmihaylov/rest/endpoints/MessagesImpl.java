package com.mmihaylov.rest.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class MessagesImpl implements Messages {

    Properties properties;

    @Autowired
    public void init() {
        properties = new Properties();
        //properties.load();
    }

    @Override
    public String paramPageIndexHasNegativeValue() {
        return null;
    }

    @Override
    public String paramPageSizeHasOutOfRangeParam() {
        return null;
    }

    @Override
    public String noEmployeeFoundByEmail(String email) {
        return null;
    }
}
