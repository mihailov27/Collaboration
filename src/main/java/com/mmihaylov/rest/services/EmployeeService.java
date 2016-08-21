package com.mmihaylov.rest.services;

import com.mmihaylov.rest.entities.Employee;
import com.mmihaylov.rest.exceptions.ResourceAlreadyExistsException;
import com.mmihaylov.rest.exceptions.ResourceNotFoundException;

public interface EmployeeService {

    /**  get employee by his id */
    Employee get(Long id) throws ResourceNotFoundException;

    /** get employee by his email */
    Employee get(String email) throws ResourceNotFoundException;

    /** create a new employee */
    Long create(Employee employee) throws ResourceAlreadyExistsException;

    /** update an existing employee */
    Employee update(Long id, Employee employee) throws ResourceNotFoundException;

    /** delete permanently an employee  */
    void delete(Long id) throws ResourceNotFoundException;

    /* use only for tests - it is dangerous operation. */
    void deleteAll();

}
