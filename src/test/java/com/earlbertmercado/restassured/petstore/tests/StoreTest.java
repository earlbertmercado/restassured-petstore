package com.earlbertmercado.restassured.petstore.tests;

import com.earlbertmercado.restassured.petstore.base.BaseTest;
import com.earlbertmercado.restassured.petstore.clients.StoreClient;
import com.earlbertmercado.restassured.petstore.payloads.Store;
import com.earlbertmercado.restassured.petstore.utils.ReportManager;
import com.earlbertmercado.restassured.petstore.utils.SchemaValidator;
import com.earlbertmercado.restassured.petstore.utils.TestDataProvider;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class StoreTest extends BaseTest {

    private final StoreClient storeClient = new StoreClient();

    @Test(
            dataProvider = "storeData",
            dataProviderClass = TestDataProvider.class,
            priority = 1
    )
    public void testOrderPet(Map<String, String> data) {
        ReportManager.logInfo("Ordering pet " + data);

        Store store = Store.builder()
                .id(Integer.parseInt(data.get("orderId")))
                .petId(Integer.parseInt(data.get("petId")))
                .quantity(Integer.parseInt(data.get("quantity")))
                .shipDate(data.get("shipDate"))
                .status(data.get("status"))
                .complete(Boolean.parseBoolean(data.get("complete")))
                .build();

        ReportManager.logInfo("Request: " + store);

        Response response = storeClient.orderPet(store);

        ReportManager.logInfo("Response: " + response.asString());
        ReportManager.logInfo("Create Response Status: " + response.statusCode());

        Assert.assertEquals(response.statusCode(), 200);
        response.then().assertThat().body(SchemaValidator.storeSchema());
    }

    @Test(
            dataProvider = "storeData",
            dataProviderClass = TestDataProvider.class,
            priority = 2
    )
    public void testFindOrderById(Map<String, String> data) {
        ReportManager.logInfo("Fetching order " + data.get("orderId"));

        Response response = storeClient.findOrderById(data.get("orderId"));

        ReportManager.logInfo("Response: " + response.asString());

        response.then().assertThat().body(SchemaValidator.storeSchema());

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("id"), data.get("orderId"));
    }

    @Test(
            dataProvider = "storeData",
            dataProviderClass = TestDataProvider.class,
            priority = 3
    )
    public void testDeleteOrderById(Map<String, String> data) {
        ReportManager.logInfo("Deleting order " + data.get("orderId"));

        Response response = storeClient.deleteOrderById(data.get("orderId"));

        ReportManager.logInfo("Response: " + response.asString());

        Assert.assertEquals(response.statusCode(), 200);
    }
}
