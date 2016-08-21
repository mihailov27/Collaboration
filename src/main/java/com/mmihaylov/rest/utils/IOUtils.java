package com.mmihaylov.rest.utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.Closeable;
import java.io.IOException;

/** Set of methods for common IO operations. */
public class IOUtils {

    private static final Logger LOGGER = LogManager.getLogger(IOUtils.class);

    /** Close the given stream as input. */
    public static void closeResource(Closeable closeable) {
        try {
            if(closeable != null)
                closeable.close();
        } catch (IOException ioExc) {
            LOGGER.error("Fail to close resource:" + ioExc.getMessage());
        }
    }
}
