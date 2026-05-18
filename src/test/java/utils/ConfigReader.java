package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility engine designed to parse external configuration parameters.
 * Eliminates hardcoded variables by externalizing runtime data (URLs, credentials, timeouts).
 */
public class ConfigReader {
    private static Properties properties;

    // Static block to load properties when the class is first accessed
    static {
        try {
            String filePath = "src/test/resources/config.properties";
            FileInputStream input = new FileInputStream(filePath);
            properties = new Properties();
            properties.load(input);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error: Cannot load config.properties file. Please check the file path and ensure it exists.");
        }
    }
    
    /**
     * Method to retrieve a property value by its key
     *
     * @param key property key 
     * @return property value
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}