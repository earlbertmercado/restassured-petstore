package com.earlbertmercado.restassured.petstore.endpoints;

public final class StoreEndpoints {

    private StoreEndpoints() {}

    public static final String ORDER_PET = "/store/order";
    public static final String FIND_ORDER_BY_ID = "/store/order/{orderId}";
    public static final String DELETE_ORDER_BY_ID = "/store/order/{orderId}";
}
