package com.mmihaylov.rest.services;

import com.mmihaylov.rest.data.repositories.EmployeeRepository;
import com.mmihaylov.rest.entities.Employee;
import com.mmihaylov.rest.exceptions.ResourceAlreadyExistsException;
import com.mmihaylov.rest.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(readOnly = true)
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee get(Long id) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findOne(id);
        if(employee == null) {
            throw new ResourceNotFoundException("No employee was found with id:" + id);
        }
        return employee;
    }

    @Transactional(readOnly = false)
    @Override
    public Long create(Employee employee) throws ResourceAlreadyExistsException {
        String employeeEmail = employee.getEmail();
        Employee potentialExistingEmployee = employeeRepository.findByEmail(employeeEmail);
        if(potentialExistingEmployee != null) {
            throw new ResourceAlreadyExistsException("An employee with same email aready exists: " + employeeEmail);
        }
        employee.setCreationDate(new Date());
        employee = employeeRepository.save(employee);
        Long employeeId = employee.getId();
        return employeeId;
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        boolean isExist = employeeRepository.exists(id);
        if(!isExist) {
            throw new ResourceNotFoundException("No user found with given id: " + id);
        }
       employeeRepository.delete(id);
    }

    @Override
    public Employee update(Long id, Employee employee) throws ResourceNotFoundException {
        boolean isExist = employeeRepository.exists(id);
        if(isExist) {
            Employee currentEmployee = employeeRepository.findOne(id);
            if(employee.getFirstName() != null) {
                currentEmployee.setFirstName(employee.getFirstName());
            }
            if(employee.getLastName() != null) {
                currentEmployee.setLastName(employee.getLastName());
            }
            if(employee.getPosition() != null) {
                currentEmployee.setPosition(employee.getPosition());
            }
            currentEmployee.setLastUpdateDate(new Date());
            currentEmployee = employeeRepository.saveAndFlush(currentEmployee);
            return currentEmployee;
        } else {
            throw new ResourceNotFoundException("No user was found with the given id: " + id);
        }
    }
}
