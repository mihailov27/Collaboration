package com.mmihaylov.rest.exceptions;

/**
 * When resource already exists.
 */
public class ResourceAlreadyExistsException extends Exception {

    public ResourceAlreadyExistsException(){
        super();
    }

    public ResourceAlreadyExistsException(String message) {
        super(message);
    }

    public ResourceAlreadyExistsException(Throwable throwable) {
        super(throwable);
    }

    public ResourceAlreadyExistsException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
