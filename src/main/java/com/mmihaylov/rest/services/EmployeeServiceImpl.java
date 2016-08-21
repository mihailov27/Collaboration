package com.mmihaylov.rest.services;

import com.mmihaylov.rest.data.repositories.EmployeeRepository;
import com.mmihaylov.rest.entities.Employee;
import com.mmihaylov.rest.exceptions.ResourceAlreadyExistsException;
import com.mmihaylov.rest.exceptions.ResourceNotFoundException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LogManager.getLogger(EmployeeServiceImpl.class);

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee get(Long id) throws ResourceNotFoundException {
        LOG.debug("Try to find an employee with id: " + id);
        Employee employee = employeeRepository.findOne(id);
        if(employee == null) {
            throw new ResourceNotFoundException("No employee was found with id:" + id);
        }
        return employee;
    }

    @Override
    public Employee get(String email) throws ResourceNotFoundException {
        LOG.debug("Try to find an employee with email address: " + email);
        Employee employee = employeeRepository.findOneByEmail(email);
        if(employee == null) {
            throw new ResourceNotFoundException("No employee was found with email:" + email);
        }
        return employee;
    }

    @Transactional(readOnly = false)
    @Modifying
    @Override
    public Long create(Employee employee) throws ResourceAlreadyExistsException {
        String employeeEmail = employee.getEmail();
        Employee potentialExistingEmployee = employeeRepository.findOneByEmail(employeeEmail);
        if(potentialExistingEmployee != null) {
            throw new ResourceAlreadyExistsException("An employee with same email aready exists: " + employeeEmail);
        }
        employee.setCreationDate(new Date());
        employee = employeeRepository.save(employee);
        Long employeeId = employee.getId();
        return employeeId;
    }

    @Transactional(readOnly = false)
    @Modifying
    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        boolean isExist = employeeRepository.exists(id);
        if(!isExist) {
            throw new ResourceNotFoundException("No user found with given id: " + id);
        }
       employeeRepository.delete(id);
    }

    @Transactional(readOnly = false)
    @Modifying
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

    @Transactional
    @Override
    public void deleteAll() {
        LOG.info("Delete all records in employee table");
        employeeRepository.deleteAll();
    }
}
