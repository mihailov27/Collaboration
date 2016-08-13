package com.mmihaylov.rest.services;

import com.mmihaylov.rest.entities.Employee;
import com.mmihaylov.rest.exceptions.ResourceAlreadyExistsException;
import com.mmihaylov.rest.exceptions.ResourceNotFoundException;

public interface EmployeeService {

    Employee get(Integer id) throws ResourceNotFoundException;

    Integer create(Employee employee) throws ResourceAlreadyExistsException;

    Employee update(Integer id, Employee employee) throws ResourceNotFoundException;

    void delete(Integer id) throws ResourceNotFoundException;
}
