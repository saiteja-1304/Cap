package com.CapstoneProject.CarbonFootprintTrack.ResponseDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
   
    private String name;
    private String email;
    private String city;
    private double totalCarbonFootprint;
}
