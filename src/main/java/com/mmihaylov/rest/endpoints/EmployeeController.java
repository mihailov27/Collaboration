package com.mmihaylov.rest.endpoints;

import com.mmihaylov.rest.entities.Employee;
import com.mmihaylov.rest.exceptions.ResourceAlreadyExistsException;
import com.mmihaylov.rest.exceptions.ResourceNotFoundException;
import com.mmihaylov.rest.services.EmployeeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController extends BaseController<Long, Employee> {

    private static final Logger LOG = Logger.getLogger(EmployeeController.class);

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        LOG.debug("Initialize employee controller.");
        this.employeeService = employeeService;
    }

    // GET BY ID
    @RequestMapping(method = RequestMethod.GET, value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity getEmployee(@PathVariable("id") Long id) {
        try {
            LOG.info("Get employee with id:" + id);
            Employee employee = employeeService.get(id);
            return ResponseEntity.ok(employee);
        } catch (ResourceNotFoundException resNotFoundException) {
            LOG.debug(resNotFoundException);
            return noResourceFound(id);
        }
    }

    // GET BY EMAIL (IT IS ASSUMED THAT EMAIL IS UNIQUE PER USER)
    @RequestMapping(method = RequestMethod.GET, value = "/get-by-email", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity getEmployee(@RequestParam("email") String email) {
        try {
            LOG.info("Get employee with email:" + email);
            Employee employee = employeeService.get(email);
            return ResponseEntity.ok(employee);
        } catch (ResourceNotFoundException resNotFoundException) {
            LOG.debug(resNotFoundException);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No employee found with given email:" + email);
        }
    }

    // POST
    @RequestMapping(method = RequestMethod.POST, value="/create")
    @ResponseBody
    public ResponseEntity create(@RequestBody Employee employee) {
        try {
            LOG.info("Creating new employee.");
            Long id = employeeService.create(employee);
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
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody Employee employee) {
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
    public ResponseEntity delete(@PathVariable("id") Long id) {
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