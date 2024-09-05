package com.CapstoneProject.CarbonFootprintTrack.service;

import com.CapstoneProject.CarbonFootprintTrack.Exceptions.UserIdNotFoundException;
import com.CapstoneProject.CarbonFootprintTrack.ResponseDto.*;
import com.CapstoneProject.CarbonFootprintTrack.client.UserClient;
import com.CapstoneProject.CarbonFootprintTrack.model.User;
import com.CapstoneProject.CarbonFootprintTrack.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private CarbonTrackRepository carbonTrackRepository;


    @Autowired
    private LeaderboardRepository leaderboardRepository;

    @Autowired
    private UserClient userClient;





    public List<User> getAllUsers() {
        return userClient.getAllUsers(); // Fetch all users using Feign client
    }

    public UserDto getUserProfile(Long userId) {
        User user = userClient.getUserById(userId); // Fetch user using Feign client

        if (user == null) {
            throw new UserIdNotFoundException(userId);
        }

        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setCity(user.getCity());
        userDto.setTotalCarbonFootprint(user.getTotalCarbonFootprint());

        return userDto;
    }
}
