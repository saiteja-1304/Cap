package com.CapstoneProject.CarbonFootprintTrack.service;

import com.CapstoneProject.CarbonFootprintTrack.Exceptions.UserIdNotFoundException;
import com.CapstoneProject.CarbonFootprintTrack.ResponseDto.*;
import com.CapstoneProject.CarbonFootprintTrack.client.UserClient;
import com.CapstoneProject.CarbonFootprintTrack.entities.CarbonFootprintForm;
import com.CapstoneProject.CarbonFootprintTrack.entities.Transportation;
import com.CapstoneProject.CarbonFootprintTrack.entities.Wastage;
import com.CapstoneProject.CarbonFootprintTrack.model.CarbonFootPrint;
import com.CapstoneProject.CarbonFootprintTrack.model.User;
import com.CapstoneProject.CarbonFootprintTrack.model.leaderBoard;
import com.CapstoneProject.CarbonFootprintTrack.repository.CarbonTrackRepository;
import com.CapstoneProject.CarbonFootprintTrack.repository.LeaderboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarbonTrackService {

    @Autowired
    private CarbonTrackRepository carbonTrackRepository;

    @Autowired
    private UserClient userFeignClient; // Feign client for User service

    @Autowired
    private LeaderboardRepository leaderboardRepository;

    // Convert LocalDate to String
    private String dateConversion(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(formatter);
    }

    public List<CarbonFootPrint> getAllCarbonDetails() {
        return carbonTrackRepository.findAll();
    }

    // Get the latest 30 records of total carbon footprint
    public List<DashboardResponseDto> getDashboard(Long userId) {
        List<CarbonFootPrint> footprints = carbonTrackRepository.findByUserId(userId);

        if (footprints.isEmpty()) {
            throw new UserIdNotFoundException(userId);
        }

        return footprints.stream()
                .sorted((f1, f2) -> f2.getToday_date().compareTo(f1.getToday_date())) // Sort by date descending
                .limit(30) // Limit to 30 records
                .map(carbonFootPrint -> {
                    DashboardResponseDto dto = new DashboardResponseDto();
                    dto.setDate(dateConversion(carbonFootPrint.getToday_date()));
                    dto.setTotalCarbonFootprint(carbonFootPrint.getCarbon_footprint());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // Get carbon footprint details emitted by only electricity
    public List<ElectricityDto> getElectricityCarbonFootprint(Long userId) {
        List<CarbonFootPrint> footprints = carbonTrackRepository.findByUserId(userId);

        if (footprints.isEmpty()) {
            throw new UserIdNotFoundException(userId);
        }

        return footprints.stream()
                .map(carbonFootPrint -> {
                    ElectricityDto dto = new ElectricityDto();
                    dto.setDate(dateConversion(carbonFootPrint.getToday_date()));
                    dto.setElectricity(carbonFootPrint.getElectricity());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // Get carbon footprint details emitted by only wastage
    public List<WastageDto> getWastageCarbonFootprint(Long userId) {
        List<CarbonFootPrint> footprints = carbonTrackRepository.findByUserId(userId);

        if (footprints.isEmpty()) {
            throw new UserIdNotFoundException(userId);
        }

        return footprints.stream()
                .map(carbonFootPrint -> {
                    WastageDto dto = new WastageDto();
                    dto.setDate(dateConversion(carbonFootPrint.getToday_date()));
                    dto.setWastage(carbonFootPrint.getWastage());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // Get carbon footprint details emitted by only transportation
    public List<TransportationDto> getTransportation(Long userId) {
        List<CarbonFootPrint> footprints = carbonTrackRepository.findByUserId(userId);

        if (footprints.isEmpty()) {
            throw new UserIdNotFoundException(userId);
        }

        return footprints.stream()
                .map(carbonFootPrint -> {
                    TransportationDto dto = new TransportationDto();
                    dto.setDate(dateConversion(carbonFootPrint.getToday_date()));
                    dto.setTransportation(carbonFootPrint.getTransportation());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // Calculate and submit carbon footprint data
    public ResponseEntity<String> calculateAndSubmit(CarbonFootprintForm form) {
        double transportationEmissions = calculateTransportationEmissions(form.getTransportations());
        double wastageEmissions = calculateWastageEmissions(form.getWastages());
        double electricityEmissions = calculateElectricityEmissions(form.getPrevWatts(), form.getTodayWatts());

        double totalEmissions = transportationEmissions + wastageEmissions + electricityEmissions;

        // Create and save CarbonFootprint entity
        CarbonFootPrint carbonFootprint = new CarbonFootPrint();
        carbonFootprint.setUserId(form.getUserId());
        carbonFootprint.setToday_date(LocalDate.now());
        carbonFootprint.setCity(form.getCity());
        carbonFootprint.setName(form.getName()); // Changed from username to name
        carbonFootprint.setTransportation(transportationEmissions);
        carbonFootprint.setWastage(wastageEmissions);
        carbonFootprint.setElectricity(electricityEmissions);
        carbonFootprint.setCarbon_footprint(totalEmissions);

        // Update user total carbon footprint via Feign client
        User user = userFeignClient.getUserById(form.getUserId());
        if (user == null) {
            throw new UserIdNotFoundException(form.getUserId());
        }
        user.setTotalCarbonFootprint(user.getTotalCarbonFootprint() + totalEmissions);
        userFeignClient.updateUser(form.getUserId(), user);

        // Update or create leaderboard entry
        leaderBoard leaderboard = leaderboardRepository.findById(form.getUserId())
                .orElseGet(() -> {
                    leaderBoard newLeaderboard = new leaderBoard();
                    newLeaderboard.setUserId(form.getUserId());
                    newLeaderboard.setName(form.getName()); // Changed from username to name
                    newLeaderboard.setCity(form.getCity());
                    return newLeaderboard;
                });
        leaderboard.setTotalCarbonFootprint(leaderboard.getTotalCarbonFootprint() + totalEmissions);
        leaderboardRepository.save(leaderboard);

        // Save CarbonFootprint record
        carbonTrackRepository.save(carbonFootprint);

        return ResponseEntity.ok("Carbon footprint submitted successfully with total emissions: " + totalEmissions);
    }


    // Calculate transportation emissions
    private double calculateTransportationEmissions(List<Transportation> transportations) {
        double emissions = 0.0;
        for (Transportation t : transportations) {
            double emissionFactor = getEmissionFactor(t.getMode());
            if (t.getDistance() != 0.0) {
                emissions += t.getDistance() * emissionFactor;
            } else if (t.getTime() != 0.0) {
                emissions += t.getTime() * (emissionFactor / 10); // Example conversion
            }
        }
        return emissions;
    }

    // Calculate wastage emissions
    private double calculateWastageEmissions(List<Wastage> wastages) {
        double emissions = 0.0;
        for (Wastage w : wastages) {
            emissions += (w.getWetWaste() + w.getDryWaste()) * 0.5; // Example emission factor
        }
        return emissions;
    }

    // Calculate electricity emissions
    private double calculateElectricityEmissions(int prevWatts, int todayWatts) {
        return (todayWatts - prevWatts) * 0.7; // Example emission factor
    }

    // Get emission factor based on mode of transportation
    private double getEmissionFactor(String mode) {
        switch (mode.toLowerCase()) {
            case "car":
                return 0.2;
            case "electric car":
                return 0.1;
            case "train":
                return 0.05;
            case "flight":
                return 0.3;
            case "bus":
                return 0.15;
            default:
                return 0.2;
        }
    }
}
