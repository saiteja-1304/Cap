package com.CapstoneProject.CarbonFootprintTrack.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LeaderBoardDto {
    private String name; // Changed from username to name
    private double totalCarbonFootprint;
    private String city;
    private String date;
}
