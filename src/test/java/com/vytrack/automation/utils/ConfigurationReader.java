package com.vytrack.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigurationReader {
    private static Properties configFile;
    private static final Logger LOGGER = LogManager.getLogger(ConfigurationReader.class);

    static {

        try {
            String path = System.getProperty("user.dir") + "/configuration.properties";
            FileInputStream input = new FileInputStream(path);
            configFile = new Properties();
            configFile.load(input);
            input.close();

        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            throw new RuntimeException("Failed to load Properties file");
        }

    }

    public static String getProperty(String keyName) {
        return configFile.getProperty(keyName);
    }
}
