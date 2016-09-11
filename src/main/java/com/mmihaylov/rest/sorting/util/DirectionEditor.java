package com.mmihaylov.rest.sorting.util;

import com.mmihaylov.rest.sorting.Direction;

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

