package com.earlbertmercado.restassured.petstore.tests;

import com.earlbertmercado.restassured.petstore.base.BaseTest;
import com.earlbertmercado.restassured.petstore.payloads.User;
import com.earlbertmercado.restassured.petstore.utils.ReportManager;
import com.earlbertmercado.restassured.petstore.utils.SchemaValidator;
import com.earlbertmercado.restassured.petstore.utils.TestDataProvider;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class UserTest extends BaseTest {

    @Test(
            dataProvider = "userData",
            dataProviderClass = TestDataProvider.class,
            priority = 1
    )
    public void testCreateUser(Map<String, String> data) {
        ReportManager.logInfo("Creating user " + data);

        User user = User.builder()
                .id(Integer.parseInt(data.get("userId")))
                .username(data.get("username"))
                .firstName(data.get("firstName"))
                .lastName(data.get("lastName"))
                .email(data.get("email"))
                .password(data.get("password"))
                .phone(data.get("phone"))
                .userStatus(Integer.parseInt(data.get("userStatus")))
                .build();

        ReportManager.logInfo("Request: " + user);

        Response response = userClient.createUser(user);

        ReportManager.logInfo("Response: " + response.asString());
        ReportManager.logInfo("Create Response Status: " + response.statusCode());

        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test(
            dataProvider = "userData",
            dataProviderClass = TestDataProvider.class,
            priority = 2
    )
    public void testGetUser(Map<String, String> data) {
        ReportManager.logInfo("Fetching user " + data.get("username"));

        Response response = userClient.getUser(data.get("username"));

        ReportManager.logInfo("Response: " + response.asString());

        response.then().assertThat().body(SchemaValidator.userSchema());

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("username"), data.get("username"));
    }

    @Test(
            dataProvider = "userData",
            dataProviderClass = TestDataProvider.class,
            priority = 3
    )
    public void testUpdateUser(Map<String, String> data) {
        ReportManager.logInfo("Updating user " + data.get("username"));

        User updatedUser = User.builder()
                .id(Integer.parseInt(data.get("userId")))
                .username(data.get("username"))
                .firstName(data.get("firstName") + "_Updated")
                .lastName(data.get("lastName") + "_Updated")
                .email("updated_" + data.get("email"))
                .password(data.get("password"))
                .phone(data.get("phone"))
                .userStatus(Integer.parseInt(data.get("userStatus")))
                .build();

        ReportManager.logInfo("Request: " + updatedUser);

        Response response = userClient.updateUser(data.get("username"), updatedUser);

        ReportManager.logInfo("Response: " + response.asString());

        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test(
            dataProvider = "userData",
            dataProviderClass = TestDataProvider.class,
            priority = 4
    )
    public void testDeleteUser(Map<String, String> data) {
        ReportManager.logInfo("Deleting user " + data.get("username"));

        Response response = userClient.deleteUser(data.get("username"));

        ReportManager.logInfo("Response: " + response.asString());

        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test(
            dataProvider = "userData",
            dataProviderClass = TestDataProvider.class,
            priority = 5
    )
    public void testCreateUserWithList(Map<String, String> data) {
        List<User> users = List.of(
                User.builder()
                        .id(1)
                        .username("john")
                        .firstName("John")
                        .lastName("Doe")
                        .email("john@mail.com")
                        .password("1234")
                        .phone("111")
                        .userStatus(1)
                        .build(),
                User.builder()
                        .id(2)
                        .username("jane")
                        .firstName("Jane")
                        .lastName("Doe")
                        .email("jane@mail.com")
                        .password("5678")
                        .phone("222")
                        .userStatus(1)
                        .build()
        );

        ReportManager.logInfo("Request: " + users);

        Response response = userClient.createUserWithList(users);

        ReportManager.logInfo("Response: " + response.asString());

        Assert.assertEquals(response.statusCode(), 200);
    }
}
