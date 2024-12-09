package com.microservices.user.controller;

import java.util.Arrays;
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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.microservices.user.entities.Hotel;
import com.microservices.user.entities.Rating;
import com.microservices.user.entities.User;
import com.microservices.user.service.UserService;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

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

    @GetMapping("/{userId}")
    // @Retry(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallBack")
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallBack")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        User gotUser = userService.getUser(userId);
        Rating[] arrayOfRatings = restTemplate.getForObject("http://RATINGSERVICE/ratings/byUser/" + userId, Rating[].class);
        List<Rating> ratings = Arrays.stream(arrayOfRatings).toList();

        ratings.forEach(rating -> {
            try {
                Hotel hotel = restTemplate.getForEntity("http://HOTELSERVICE/hotels/" + rating.getHotelId(), Hotel.class).getBody();
                rating.setHotel(hotel);
            } catch (RestClientException e) {
                System.err.println("Failed to fetch hotel for Rating ID " + rating.getHotelId() + ": " + e.getMessage());
                // Optionally, set a default or null Hotel
                rating.setHotel(null);
            }
        });

        gotUser.setRatings(ratings);
        return ResponseEntity.status(HttpStatus.FOUND).body(gotUser);
    }

    int counter = 0;

    public ResponseEntity<User> ratingHotelFallBack(String userId, Exception ex) {
        System.out.println("Retry Count: " + counter);
        counter++;
        User user = User.builder().email("dummy@dum.com").about("Dummy").name("Mr Dummy").userId(userId).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(user);
    }

    @GetMapping()
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAllUser();
        for (User user : users) {
            Rating[] arrayOfRatings = restTemplate.getForObject("http://RATINGSERVICE/ratings/byUser/" + user.getUserId(), Rating[].class);
            List<Rating> ratings = Arrays.stream(arrayOfRatings).toList();

            ratings.forEach(rating -> {
                try {
                    Hotel hotel = restTemplate.getForEntity("http://HOTELSERVICE/hotels/" + rating.getHotelId(), Hotel.class).getBody();
                    rating.setHotel(hotel);
                } catch (RestClientException e) {
                    System.err.println("Failed to fetch hotel for Rating ID " + rating.getHotelId() + ": " + e.getMessage());
                    // Optionally, set a default or null Hotel
                    rating.setHotel(null);
                }
            });
            user.setRatings(ratings);
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
