package com.earlbertmercado.restassured.petstore.clients;

import com.earlbertmercado.restassured.petstore.endpoints.PetEndpoints;
import com.earlbertmercado.restassured.petstore.payloads.Pet;

import io.restassured.response.Response;

public class PetClient extends BaseClient {

    public Response addPet(Pet pet) {
        return executePost(PetEndpoints.ADD_NEW_PET, pet);
    }

    public Response findPetById(String petId) {
        return executeGet(PetEndpoints.FIND_PET_BY_ID, petId);
    }

    public Response updatePet(Pet pet) {
        return executePut(PetEndpoints.UPDATE_PET, pet);
    }

    public Response deletePet(String petId) {
        return executeDelete(PetEndpoints.DELETE_PET, petId);
    }
}
