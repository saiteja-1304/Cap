package com.CapstoneProject.CarbonFootprintTrack.Exceptions;

public class UserIdNotFoundException extends RuntimeException{
    public UserIdNotFoundException(Long userId ) {
        super("User with UserId : " + userId + " is not available");
    }
}
