package com.mmihaylov.rest.endpoints;

import com.mmihaylov.rest.entities.Employee;
import com.mmihaylov.rest.exceptions.ResourceAlreadyExistsException;
import com.mmihaylov.rest.exceptions.ResourceNotFoundException;
import com.mmihaylov.rest.services.EmployeeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController extends BaseController<Integer, Employee> {

    private static final Logger LOG = Logger.getLogger(EmployeeController.class);

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        LOG.debug("Initialize employee controller.");
        this.employeeService = employeeService;
    }

    // GET
    @RequestMapping(method = RequestMethod.GET, value = "/get/{id}")
    @ResponseBody
    public ResponseEntity getEmployee(@PathVariable("id") Integer id) {
        try {
            LOG.info("Get employee with id:" + id);
            Employee employee = employeeService.get(id);
            return ResponseEntity.ok(employee);
        } catch (ResourceNotFoundException resNotFoundException) {
            LOG.debug(resNotFoundException);
            return noResourceFound(id);
        }
    }

    // POST
    @RequestMapping(method = RequestMethod.POST, value="/create")
    @ResponseBody
    public ResponseEntity create(@RequestBody Employee employee) {
        try {
            LOG.info("Creating new employee.");
            Integer id = employeeService.create(employee);
            LOG.info("New employee was created with id:" + id);
            return ResponseEntity.noContent().header("Location", String.valueOf(id)).build();
        } catch (ResourceAlreadyExistsException resExistException) {
            LOG.debug(resExistException);
            return conflictResponse();
        }
    }

    // PUT
    @RequestMapping(method = RequestMethod.PUT, value="/update/{id}")
    @ResponseBody
    public ResponseEntity update(@PathVariable("id") Integer id, @RequestBody Employee employee) {
        try {
            LOG.debug("Update employee with id:" + id);
            Employee updatedEmployee = employeeService.update(id, employee);
            return ResponseEntity.ok(updatedEmployee);
        } catch (ResourceNotFoundException resNotFoundException) {
            LOG.debug(resNotFoundException);
            return noResourceFound(id);
        }
    }


    // DELETE
    @RequestMapping(method = RequestMethod.DELETE, value="/delete/{id}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        try {
            LOG.debug("Delete employee with id:" + id);
            employeeService.delete(id);
            return ResponseEntity.ok("Resource with id: " + id + " was successfully deleted.");
        } catch(ResourceNotFoundException resNotFoundException) {
            LOG.debug(resNotFoundException);
            return noResourceFound(id);
        }
    }
}