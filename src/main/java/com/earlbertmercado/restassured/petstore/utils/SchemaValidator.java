package com.earlbertmercado.restassured.petstore.utils;

import com.earlbertmercado.restassured.petstore.config.ConfigManager;
import org.hamcrest.Matcher;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class SchemaValidator {

    private static final String USER_SCHEMA= "userSchema.json";
    private static final String PET_SCHEMA= "petSchema.json";
    private static final String STORE_SCHEMA= "storeSchema.json";

    public static Matcher<?> userSchema() {
        String path = ConfigManager.getSchemaPath() + USER_SCHEMA;
        return matchesJsonSchemaInClasspath(path);
    }

    public static Matcher<?> petSchema() {
        String path = ConfigManager.getSchemaPath() + PET_SCHEMA;
        return matchesJsonSchemaInClasspath(path);
    }

    public static Matcher<?> storeSchema() {
        String path = ConfigManager.getSchemaPath() + STORE_SCHEMA;
        return matchesJsonSchemaInClasspath(path);
    }
 }