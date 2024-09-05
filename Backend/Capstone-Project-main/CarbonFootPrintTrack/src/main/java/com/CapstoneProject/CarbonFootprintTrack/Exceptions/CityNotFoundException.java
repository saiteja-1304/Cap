package com.CapstoneProject.CarbonFootprintTrack.Exceptions;

public class CityNotFoundException extends RuntimeException{
        public CityNotFoundException(String city) {
            super("City with " + city + " not found ");
        }
    }

