package com.mmihaylov.rest.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController<ID, T> {

    protected final ResponseEntity createResponse(T entity) {
        if(entity == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(entity);
        }
    }

    protected final ResponseEntity noResourceFound(ID id) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No resource found with given id:" + id);
    }

    protected final ResponseEntity conflictResponse() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict with existing resource.");
    }

    protected final ResponseEntity badRequest(String errorMessage) {
        return ResponseEntity.badRequest().body(errorMessage);
    }
}
