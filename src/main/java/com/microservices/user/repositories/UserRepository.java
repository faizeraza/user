package com.microservices.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservices.user.entities.User;

public interface UserRepository extends JpaRepository<User, String> {

}
