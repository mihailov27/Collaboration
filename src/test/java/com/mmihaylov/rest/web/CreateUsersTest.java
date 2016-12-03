package com.mmihaylov.rest.web;

import com.mmihaylov.rest.utils.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.*;

public class CreateUsersTest {

    private static final Logger LOG = LogManager.getLogger(CreateUsersTest.class);

    @Test
    public void printUsers() {

        File csvWithData = new File("src/test/resources/test-data/employees.csv");
        if(!csvWithData.exists() || !csvWithData.isFile())
            throw new IllegalStateException("The file doest exist or it is not a file, but a directory.");
        InputStream isWithCsv = null;
        try {
            isWithCsv = new FileInputStream(csvWithData);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(isWithCsv));
            String headersLine = bufferedReader.readLine();
            String headers[] = headersLine.split(",");
            int numOfHeaders = headers.length;
            String line = null;
            while((line = bufferedReader.readLine()) != null && !line.trim().isEmpty()) {
                String[] employeeValues = line.split(",");
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                for(int i=0; i < numOfHeaders; i++) {
                    sb.append(headers[i] + ":" +employeeValues[i]);
                    if(i < numOfHeaders - 1) {
                        sb.append(",");
                    }
                }
                sb.append("]");
                LOG.info(sb.toString());
            }

        } catch(FileNotFoundException fileNotFoundExc) {
            LOG.error(fileNotFoundExc.getMessage());
        } catch(IOException ioExc) {
            LOG.error(ioExc.getMessage());
        } finally {
            IOUtils.closeResource(isWithCsv);
        }
    }
}
