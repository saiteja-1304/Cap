package com.CapstoneProject.CarbonFootprintTrack.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Electricity {
    private int previouswatts;
    private int currentwatts;
}
