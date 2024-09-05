package com.serviceharbor.auth.service;


import com.serviceharbor.auth.dtos.LoginUserDto;
import com.serviceharbor.auth.dtos.RegisterUserDto;
import com.serviceharbor.auth.model.Role;
import com.serviceharbor.auth.model.User;
import com.serviceharbor.auth.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    public User signup(RegisterUserDto input) {
//        var user = new User()
//
//            .setEmail(input.getEmail())
//            .setPassword(passwordEncoder.encode(input.getPassword()));
//
//        return userRepository.save(user);
//    }


    public User signup(RegisterUserDto input) {
        // Create a new User object
        User user = new User();

        // Set fields in the User object from the RegisterUserDto
        user.setName(input.getName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword())); // Assuming passwordEncoder is defined elsewhere
        user.setCity(input.getCity());

        // Set default values for fields not present in the RegisterUserDto
        user.setRole(Role.USER); // Assuming a default role of USER; adjust as needed
        user.setTotalCarbonFootprint(0.0); // Initialize totalCarbonFootprint to 0.0

        // Save the user in the database
        return userRepository.save(user);
    }


    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail()).orElseThrow();
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }
}
