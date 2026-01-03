package com.earlbertmercado.restassured.petstore.tests;

import com.earlbertmercado.restassured.petstore.base.BaseTest;
import com.earlbertmercado.restassured.petstore.clients.PetClient;
import com.earlbertmercado.restassured.petstore.payloads.Pet;
import com.earlbertmercado.restassured.petstore.utils.ReportManager;
import com.earlbertmercado.restassured.petstore.utils.SchemaValidator;
import com.earlbertmercado.restassured.petstore.utils.TestDataProvider;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class PetTest extends BaseTest {

    private final PetClient petClient = new PetClient();

    @Test(
            dataProvider = "petData",
            dataProviderClass = TestDataProvider.class,
            priority = 1
    )
    public void testAddNewPet(Map<String, String> data) {
        ReportManager.logInfo("Adding new pet " + data);

        Pet pet = Pet.builder()
                .id(Integer.parseInt(data.get("petId")))
                .name(data.get("name"))
                .status(data.get("status"))
                .category(
                        Pet.Category.builder()
                                .id(Integer.parseInt(data.get("categoryId")))
                                .name(data.get("categoryName"))
                                .build()
                )
                .photoUrls(List.of(data.get("photoUrls")))
                .tags(
                        List.of(
                                Pet.Tag.builder()
                                        .id(1)
                                        .name("friendly")
                                        .build()
                        )
                )
                .build();

        ReportManager.logInfo("Request: " + pet);

        Response response = petClient.addPet(pet);

        ReportManager.logInfo("Response: " + response.asString());
        ReportManager.logInfo("Create Response Status: " + response.statusCode());

        Assert.assertEquals(response.statusCode(), 200);
        response.then().assertThat().body(SchemaValidator.petSchema());
    }

    @Test(
            dataProvider = "petData",
            dataProviderClass = TestDataProvider.class,
            priority = 2
    )
    public void testFindPetById(Map<String, String> data) {
        ReportManager.logInfo("Fetching pet " + data.get("petId"));

        Response response = petClient.findPetById(data.get("petId"));

        ReportManager.logInfo("Response: " + response.asString());

        response.then().assertThat().body(SchemaValidator.petSchema());

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("id"), data.get("petId"));
    }

    @Test(
            dataProvider = "petData",
            dataProviderClass = TestDataProvider.class,
            priority = 3
    )
    public void testUpdatePet(Map<String, String> data) {
        ReportManager.logInfo("Updating pet " + data.get("petId"));

        Pet updatedPetStatus = Pet.builder()
                .name(data.get("name"))
                .status("unavailable")
                .category(
                        Pet.Category.builder()
                                .id(Integer.parseInt(data.get("categoryId")))
                                .name(data.get("categoryName"))
                                .build()
                )
                .photoUrls(List.of(data.get("photoUrls")))
                .tags(
                        List.of(
                                Pet.Tag.builder()
                                        .id(1)
                                        .name("friendly")
                                        .build()
                        )
                )
                .build();

        ReportManager.logInfo("Request: " + updatedPetStatus);

        Response response = petClient.updatePet(updatedPetStatus);

        ReportManager.logInfo("Response: " + response.asString());

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("status"), "unavailable");
    }

    @Test(
            dataProvider = "petData",
            dataProviderClass = TestDataProvider.class,
            priority = 4
    )
    public void testDeletePet(Map<String, String> data) {
        ReportManager.logInfo("Deleting pet " + data.get("petId"));

        Response response = petClient.deletePet(data.get("petId"));

        ReportManager.logInfo("Response: " + response.asString());

        Assert.assertEquals(response.statusCode(), 200);
    }
}
