package com.microservices.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.user.entities.User;
import com.microservices.user.exceptions.RecordNotFoundException;
import com.microservices.user.repositories.UserRepository;
import com.microservices.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RecordNotFoundException("No Record Found On Server for UserId + " + userId));
    }

    @Override
    public User delete(String userId) {
        User tobeDeleted = userRepository.findById(userId).get();
        userRepository.delete(tobeDeleted);
        return tobeDeleted;
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

}
