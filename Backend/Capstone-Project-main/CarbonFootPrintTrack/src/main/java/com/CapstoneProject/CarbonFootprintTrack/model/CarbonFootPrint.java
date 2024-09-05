package com.CapstoneProject.CarbonFootprintTrack.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "carbondetails")
public class CarbonFootPrint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Use Long instead of long

    private Long userId; // Use Long for consistency and to handle null values
    private String name; // Changed from username to name
    private LocalDate today_date; // Consider using a proper date type for this field
    private String city;
    private double transportation;
    private double electricity;
    private double wastage;
    private double carbon_footprint;

    // Set a default value for totalCarbonFootprint
    private double totalCarbonFootprint = 0.0;
}
