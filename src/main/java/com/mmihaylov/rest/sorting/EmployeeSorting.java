package com.mmihaylov.rest.sorting;

/**
 * Created by mmihaylov on 9/3/16.
 */
public enum EmployeeSorting {

    /** Sorting by first name */
    FIRST_NAME("first_name"),

    /** Sorting by last name */
    LAST_NAME("last_email"),

    /** Sorting by date of birth */
    DATE_OF_BIRTH("date_of_birth"),

    /** Sorting by email */
    EMAIL("email");

    String column;

    private EmployeeSorting(String column) {
        this.column = column;
    }

    public String getColumn() {
        return column;
    }
}
