package com.mmihaylov.rest.services;

import com.mmihaylov.rest.entities.Employee;
import com.mmihaylov.rest.exceptions.ResourceAlreadyExistsException;
import com.mmihaylov.rest.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    int totalCount = 0;
    Map<Integer, Employee> employeeMap = new HashMap<Integer, Employee>();

    @Override
    public Employee get(Integer id) throws ResourceNotFoundException {
        Employee employee = employeeMap.get(id);
        return employee;
    }

    @Override
    public Integer create(Employee employee) throws ResourceAlreadyExistsException {
        int currentId = totalCount;
        employee.setId(currentId);
        employee.setCreationDate(new Date());
        employeeMap.put(currentId, employee);
        totalCount += 1;
        return currentId;
    }

    @Override
    public void delete(Integer id) throws ResourceNotFoundException {
        if(employeeMap.containsKey(id)) {
            employeeMap.remove(id);
        } else {
            throw new ResourceNotFoundException("No user was found with the given id: " + id);
        }
    }

    @Override
    public Employee update(Integer id, Employee employee) throws ResourceNotFoundException {
        if(employeeMap.containsKey(id)) {
            Employee currentEmployee = employeeMap.get(id);
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
            return currentEmployee;
        } else {
            throw new ResourceNotFoundException("No user was found with the given id: " + id);
        }
    }
}
