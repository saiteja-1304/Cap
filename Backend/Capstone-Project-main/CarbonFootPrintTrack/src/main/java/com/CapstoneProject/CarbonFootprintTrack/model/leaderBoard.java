package com.CapstoneProject.CarbonFootprintTrack.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="leaderboard")
public class leaderBoard {
    @Id
    private Long userId; // Use Long for consistency and to handle null values

    private String name; // Updated field name from username to name

    private LocalDate todayDate; // Changed from String to LocalDate for proper date handling

    private String city;

    private double totalCarbonFootprint; // Updated field name to camelCase
}
