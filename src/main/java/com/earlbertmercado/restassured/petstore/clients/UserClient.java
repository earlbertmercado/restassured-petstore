package com.earlbertmercado.restassured.petstore.clients;

import com.earlbertmercado.restassured.petstore.endpoints.UserEndpoints;
import com.earlbertmercado.restassured.petstore.payloads.User;
import io.restassured.response.Response;

import java.util.List;

public class UserClient extends BaseClient {

    public Response createUser(User user) {
        return executePost(UserEndpoints.CREATE_USER, user);
    }

    public Response createUserWithList(List<User> users) {
        return executePost(UserEndpoints.CREATE_USER_WITH_LIST, users);
    }

    public Response getUser(String username) {
        return executeGet(UserEndpoints.GET_USER, username);
    }

    public Response updateUser(String username, User user) {
        return executePut(UserEndpoints.UPDATE_USER, user, username);
    }

    public Response deleteUser(String username) {
        return executeDelete(UserEndpoints.DELETE_USER, username);
    }


}
