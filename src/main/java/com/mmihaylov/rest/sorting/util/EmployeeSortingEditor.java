package com.mmihaylov.rest.sorting.util;

import com.mmihaylov.rest.sorting.EmployeeSorting;

import java.beans.PropertyEditorSupport;

public class EmployeeSortingEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        String textValue = text.toUpperCase();
        EmployeeSorting employeeSorting = EmployeeSorting.valueOf(textValue);
        setValue(employeeSorting);
    }
}
