package com.mmihaylov.rest.endpoints;

import com.mmihaylov.rest.endpoints.sorting.Direction;
import com.mmihaylov.rest.endpoints.sorting.EmployeeSorting;
import com.mmihaylov.rest.endpoints.util.DirectionEditor;
import com.mmihaylov.rest.endpoints.util.EmployeeSortingEditor;
import com.mmihaylov.rest.entities.Employee;
import com.mmihaylov.rest.exceptions.ResourceAlreadyExistsException;
import com.mmihaylov.rest.exceptions.ResourceNotFoundException;
import com.mmihaylov.rest.services.EmployeeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController extends BaseController<Long, Employee> {

    private static final Logger LOG = Logger.getLogger(EmployeeController.class);

    private EmployeeService employeeService;

    private Messages messages;

    @Autowired
    public EmployeeController(EmployeeService employeeService, Messages messages) {
        LOG.debug("Initialize employee controller.");
        this.employeeService = employeeService;
        this.messages = messages;
    }

    /* add support for auto-mapping between text parameters and enums */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(EmployeeSorting.class, new EmployeeSortingEditor());
        binder.registerCustomEditor(Direction.class, new DirectionEditor());
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

    /* Get a list of employed based on the provided sorting and filters. */
    @RequestMapping(method = RequestMethod.GET, value="/get-by-filter", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity getEmployees(
            @RequestParam(value = "page-index", defaultValue = "0", required = false) int pageIndex,
            @RequestParam(value = "page-size", defaultValue = "25", required = false) int pageSize /* Page size is a positive value between 1 and 25*/,
            @RequestParam(value = "sort-by", defaultValue = "email", required = false) EmployeeSorting sortBy,
            @RequestParam(value = "direction", defaultValue = "asc", required = false) Direction direction) {

        if(pageIndex < 0) {
            return badRequest("Page index cannot be a negative integer. Provided value is: " + pageIndex);
        }
        if(pageSize <= 0 || pageSize > 40) {
            return badRequest("Request parameter 'page-size' must be a positive integer between range of 1 and 40.");
        }
        /*
        StringBuilder response = new StringBuilder();
        response.append("page: " + pageIndex);
        response.append(", size: " + pageSize);
        response.append(", sort by: " + sortBy.toString());
        response.append(", direction: " + direction.toString());
        */
        List<Employee> employeeList = employeeService.getEmployees(pageIndex, pageSize, sortBy, direction);
        return ResponseEntity.ok(employeeList);
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