package com.mmihaylov.rest.endpoints.util;

import com.mmihaylov.rest.endpoints.sorting.Direction;
import com.mmihaylov.rest.endpoints.sorting.EmployeeSorting;

import java.beans.PropertyEditorSupport;

/**
 * Created by mmihaylov on 9/3/16.
 */
public class DirectionEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        String textValue = text.toUpperCase();
        Direction direction = Direction.valueOf(textValue);
        setValue(direction);
    }
}

