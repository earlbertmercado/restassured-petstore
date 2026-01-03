package com.earlbertmercado.restassured.petstore.endpoints;

public final class UserEndpoints {

    private UserEndpoints() {}

    public static final String CREATE_USER = "/user";
    public static final String CREATE_USER_WITH_LIST = "/user/createWithList";
    public static final String GET_USER = "/user/{username}";
    public static final String UPDATE_USER = "/user/{username}";
    public static final String DELETE_USER = "/user/{username}";
}
