package com.earlbertmercado.restassured.petstore.clients;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.restassured.RestAssured.given;

public abstract class BaseClient {

    protected static final Logger logger =
            LogManager.getLogger(BaseClient.class);

    protected Response executeGet(String endpoint, Object... params) {
        logger.info("Executing GET request to: {}", endpoint);

        return given()
                .when()
                .get(endpoint, params);
    }

    protected Response executePost(String endpoint, Object body) {
        logger.info("Executing POST request to: {}", endpoint);

        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(body)
                .post(endpoint);
    }

    protected Response executePut(String endpoint, Object body, Object... pathValues) {
        logger.info("Executing PUT request to: {}", endpoint);

        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put(endpoint, pathValues);
    }

    protected Response executeDelete(String endpoint, Object... values) {
        logger.info("Executing DELETE request to: {}", endpoint);

        return given()
                .delete(endpoint, values);
    }
}
