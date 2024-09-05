package com.CapstoneProject.CarbonFootprintTrack.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transportation {
    private String mode;
    private double distance;
    private double time;
}
