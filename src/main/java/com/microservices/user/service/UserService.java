package com.microservices.user.service;

import java.util.List;

import com.microservices.user.entities.User;

public interface UserService {

    //Create
    User saveUser(User user);

    // get All users
    List<User> getAllUser();

    // get User by userId
    User getUser(String userId);

    // delete user by userid
    User delete(String userId);

    // update user 
    User updateUser(User user);
}
