package com.earlbertmercado.restassured.petstore.clients;

import com.earlbertmercado.restassured.petstore.endpoints.StoreEndpoints;
import com.earlbertmercado.restassured.petstore.payloads.Store;
import io.restassured.response.Response;

public class StoreClient extends BaseClient {

    public Response orderPet(Store store) {
        return executePost(StoreEndpoints.ORDER_PET, store);
    }

    public Response findOrderById(String orderId) {
        return executeGet(StoreEndpoints.FIND_ORDER_BY_ID, orderId);
    }

    public Response deleteOrderById(String orderId) {
        return executeDelete(StoreEndpoints.DELETE_ORDER_BY_ID, orderId);
    }
}
