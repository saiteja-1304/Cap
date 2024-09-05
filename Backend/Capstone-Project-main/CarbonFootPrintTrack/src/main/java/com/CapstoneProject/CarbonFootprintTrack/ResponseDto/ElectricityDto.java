package com.CapstoneProject.CarbonFootprintTrack.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ElectricityDto {
    private String date;
    private double electricity;
}
