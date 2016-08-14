package com.mmihaylov.rest.services;

import com.mmihaylov.rest.entities.Employee;
import com.mmihaylov.rest.exceptions.ResourceAlreadyExistsException;
import com.mmihaylov.rest.exceptions.ResourceNotFoundException;

public interface EmployeeService {

    Employee get(Long id) throws ResourceNotFoundException;

    Long create(Employee employee) throws ResourceAlreadyExistsException;

    Employee update(Long id, Employee employee) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;
}
