package com.mmihaylov.rest.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmihaylov.rest.data.repositories.EmployeeRepository;
import com.mmihaylov.rest.entities.Employee;
import com.mmihaylov.rest.entities.Gender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring-context.xml")
@WebAppConfiguration
public class EmployeeControllerTest {

    private ObjectMapper objectMapper;
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    EmployeeRepository employeeRepository;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        this.objectMapper = new ObjectMapper();
        // drop all existing data on the server
        employeeRepository.deleteAll();
    }



    @Test
    public void createEmployeeAndGetAfter() throws Exception {
        Employee employee = new Employee();
        employee.setFirstName("Mihail");
        employee.setLastName("Mihaylov");
        employee.setEmail("mmihaylov@gmail.com");
        employee.setPosition("Java developer");
        // date of birth
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 1989);
        calendar.set(Calendar.MONTH, 4);
        calendar.set(Calendar.DATE, 29);
        Date date = calendar.getTime();
        employee.setDateOfBirth(date);
        //gender
        employee.setGender(Gender.male);

        // post
        String employeeAsJson = objectMapper.writeValueAsString(employee);
        mockMvc.perform(post("/employee/create").content(employeeAsJson).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
                //.andExpect(header().string("Location", "1"));
//        Thread.currentThread().sleep(2000);

        // get
        mockMvc.perform(get("/employee/get-by-email?email=mmihaylov@gmail.com"))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Mihail")))
                .andExpect(jsonPath("$.lastName", is("Mihaylov")))
                .andExpect(jsonPath("$.email", is("mmihaylov@gmail.com")))
                .andExpect(jsonPath("$.position", is("Java developer")));
    }
}