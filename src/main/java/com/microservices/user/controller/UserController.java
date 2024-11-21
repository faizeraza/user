package com.microservices.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.microservices.user.entities.Rating;
import com.microservices.user.entities.User;
import com.microservices.user.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RestTemplate restTemplate;

    //create the user
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        User gotUser = userService.getUser(userId);
        ArrayList<Rating> ratings = restTemplate.getForObject("http://localhost:8083/ratings/byUser/" + userId, ArrayList.class);
        gotUser.setRatings(ratings);
        return ResponseEntity.status(HttpStatus.FOUND).body(gotUser);
    }

    @SuppressWarnings("unchecked")
    @GetMapping()
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAllUser();
        for (User user : users) {
            user.setRatings(restTemplate.getForObject("http://localhost:8083/ratings/byUser/" + user.getUserId(), ArrayList.class));
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(users);
    }

    @PutMapping()
    public ResponseEntity<User> putMethodName(@RequestBody User user) {
        User updated = userService.updateUser(user);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{uuId}")
    public ResponseEntity<User> putMethodName(@PathVariable String uuId) {
        User deleted = userService.delete(uuId);
        return ResponseEntity.ok(deleted);
    }
}
