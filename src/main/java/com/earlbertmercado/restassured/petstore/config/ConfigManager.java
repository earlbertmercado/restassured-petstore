package com.earlbertmercado.restassured.petstore.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    private static final Properties properties = new Properties();
    private static final String CONFIG_FILE = "config.properties";

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (InputStream input = ConfigManager.class
                .getClassLoader()
                .getResourceAsStream(CONFIG_FILE)) {

            if (input == null) {
                throw new RuntimeException("Configuration file not found: " + CONFIG_FILE);
            }

            properties.load(input);

            // Override with system properties
            properties.putAll(System.getProperties());

        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties", e);
        }
    }

    public static String getBaseUri() {
        return properties.getProperty(
                "base.uri",
                "https://petstore.swagger.io/v2"
        );
    }

    public static String getTestDataPath() {
        return properties.getProperty(
                "testdata.path",
                "src/test/resources/testdata/"
        );
    }

    public static String getReportPath() {
        return properties.getProperty(
                "report.path",
                "reports/"
        );
    }

    public static String getSchemaPath() {
        return properties.getProperty(
                "schema.path",
                "src/main/resources/schemas/"
        );
    }
}
