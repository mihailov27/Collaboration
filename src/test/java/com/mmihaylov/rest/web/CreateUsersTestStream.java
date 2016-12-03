package com.mmihaylov.rest.web;

import com.mmihaylov.rest.BaseConfiguration;
import com.mmihaylov.rest.entities.Employee;
import com.mmihaylov.rest.entities.Gender;
import com.mmihaylov.rest.utils.IOUtils;
import org.junit.Assert;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Stream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BaseConfiguration.class})
public class CreateUsersTestStream {

    private static final Logger LOG = LogManager.getLogger(CreateUsersTestStream.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${webserver.host}")
    private String webserverAddress;

    @Value("${webapp.domain}")
    private String webappDomain;

    @Value("${}")
    private String profileImagesDir;

    private ExecutorService executorService;

    @Before
    public void setUp() {
        LOG.debug("Set up resources before start the test cases.");
        executorService = Executors.newFixedThreadPool(3);

    }

    @After
    public void tearDown() {
        LOG.debug("Closing resources after all test cases are finished.");
    }

    private void createEmployee(Employee employee) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(webserverAddress);
        urlBuilder.append(webappDomain);
        urlBuilder.append("/employee/create");
        String url = urlBuilder.toString();
        LOG.debug("Invoke URL: " + url);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, employee, String.class);
        LOG.info("Response is: " + responseEntity.getBody());
    }

    @Test
    public void createUsers() {
        // try to call 'http://localhost:8080/SpringWebApp/employee/get-by-email?email=mmihaylov@gmail.com'
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(webserverAddress);
        urlBuilder.append(webappDomain);
        urlBuilder.append("/employee/get-by-email?email=mmihaylov@gmail.com");
        String url = urlBuilder.toString();
        LOG.debug("Invoke URL: " + url);
        ResponseEntity<Employee> employeeEntity = restTemplate.getForEntity(url, Employee.class);
        Employee employee = employeeEntity.getBody();
        Assert.assertNotNull(employee);
    }


    @Test
    public void printUsers() {

        File csvWithData = new File("src/test/resources/test-data/employees.csv");
        if(!csvWithData.exists() || !csvWithData.isFile())
            throw new IllegalStateException("The file doest exist or it is not a file, but a directory.");
        InputStream isWithCsv = null;
        InputStreamReader streamReader = null;
        BufferedReader bufferedReader = null;
        try {
            isWithCsv = new FileInputStream(csvWithData);
            streamReader  = new InputStreamReader(isWithCsv);
            bufferedReader = new BufferedReader(streamReader);
            Stream<String> lines = bufferedReader.lines();
            Map<String, String> keys = null;
            lines.skip(1).forEach(new Consumer<String>() {
                @Override
                public void accept(String line) {
                    parseCsvLineAndCreateEmployee(line);
                }
            });
        } catch(FileNotFoundException fileNotFoundExc) {
            LOG.error(fileNotFoundExc.getMessage());
        } catch(IOException ioExc) {
            LOG.error(ioExc.getMessage());
        } finally {
            IOUtils.closeResource(isWithCsv);
            IOUtils.closeResource(streamReader);
            IOUtils.closeResource(bufferedReader);
        }
    }

    private void parseCsvLineAndCreateEmployee(String line) {
        try {
            String[] properties = line.split(",");
            Employee employee = new Employee();
            String firstName = properties[4];
            employee.setFirstName(firstName.trim());
            String lastName = properties[6];
            employee.setLastName(lastName.trim());
            String email = properties[14];
            employee.setEmail(email.toLowerCase().trim());
            String position = properties[34];
            employee.setPosition(position.trim().replaceAll("\"", ""));
            // date of birth
            String dateOfBirth = properties[22];
            if(!dateOfBirth.contains("/"))
                return;
            String dateOfBirthParts[] = dateOfBirth.split("/");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, Integer.valueOf(dateOfBirthParts[2]));
            calendar.set(Calendar.MONTH, Integer.valueOf(dateOfBirthParts[1]) - 1);
            calendar.set(Calendar.DATE, Integer.valueOf(dateOfBirthParts[0]));
            Date date = calendar.getTime();
            employee.setDateOfBirth(date);
            //gender
            String genderTxt = properties[1];
            Gender gender = Gender.valueOf(genderTxt);
            employee.setGender(gender);
            employee.setProfileImage(getImage(gender));
            createEmployee(employee);
        } catch (Exception e) {
            LOG.warn(e);
        }
    }

    private String getImage(Gender gender) {

        return null;
    }
}
