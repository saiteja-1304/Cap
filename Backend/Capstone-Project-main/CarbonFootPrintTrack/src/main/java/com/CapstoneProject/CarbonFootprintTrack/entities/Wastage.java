package com.CapstoneProject.CarbonFootprintTrack.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Wastage {
    private double wetWaste;
    private double dryWaste;
}
