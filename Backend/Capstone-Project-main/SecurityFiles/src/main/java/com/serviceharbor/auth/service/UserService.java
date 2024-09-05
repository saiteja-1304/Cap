package com.serviceharbor.auth.service;

import com.serviceharbor.auth.model.User;
import com.serviceharbor.auth.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        // Convert Iterable<User> to List<User>
        Iterable<User> usersIterable = userRepository.findAll();
        return StreamSupport.stream(usersIterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

    public User updateUser(Long userId, User updatedUser) {
        // Check if the user exists
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Update user details
        existingUser.setName(updatedUser.getName());
        existingUser.setTotalCarbonFootprint(updatedUser.getTotalCarbonFootprint());
        // Add other fields as needed

        // Save the updated user
        return userRepository.save(existingUser);
    }
}
