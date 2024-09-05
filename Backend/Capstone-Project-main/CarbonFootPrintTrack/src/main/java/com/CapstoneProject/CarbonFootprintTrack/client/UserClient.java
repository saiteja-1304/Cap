package com.CapstoneProject.CarbonFootprintTrack.client;

import com.CapstoneProject.CarbonFootprintTrack.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "UserManagement", url = "http://localhost:9999")
public interface UserClient {
    @GetMapping("/users")
    List<User> getAllUsers();

    @GetMapping("/users/{userId}")
    User getUserById(@PathVariable("userId") Long userId);

    @PutMapping("/users/{id}")
    User updateUser(@PathVariable("id") Long userId, @RequestBody User user);
}

