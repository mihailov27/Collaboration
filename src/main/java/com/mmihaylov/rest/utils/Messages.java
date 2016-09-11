package com.mmihaylov.rest.utils;

public interface Messages {

    /** error message saying that no employee is found by the given email address. */
    String noEmployeeFoundByEmail(String email);

    /** error message saying that the page parameter has a negative value. It must be 0 or positive integer. */
    String paramPageIndexHasNegativeValue();

    /** Param page-size must be in a range between 1-40. */
    String paramPageSizeHasOutOfRangeParam();
}
