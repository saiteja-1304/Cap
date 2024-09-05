package com.CapstoneProject.CarbonFootprintTrack.service;

import com.CapstoneProject.CarbonFootprintTrack.Exceptions.CityNotFoundException;
import com.CapstoneProject.CarbonFootprintTrack.model.CarbonFootPrint;

import com.CapstoneProject.CarbonFootprintTrack.model.leaderBoard;
import com.CapstoneProject.CarbonFootprintTrack.repository.CarbonTrackRepository;
import com.CapstoneProject.CarbonFootprintTrack.repository.LeaderboardRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaderBoardService {
    @Autowired
    private CarbonTrackRepository carbonTrackRepository;



    @Autowired
    private LeaderboardRepository leaderboardRepository;

    public String DateConversion(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(formatter);
    }

    public List<leaderBoard> getLeaderBoard(String city) {

        List<leaderBoard> leaderboard = leaderboardRepository.findAllByCity(city);

        if (leaderboard.isEmpty()) {
            throw new CityNotFoundException(city);
        }

        return leaderboard.stream()
                .sorted((lb1, lb2) -> Double.compare(lb2.getTotalCarbonFootprint(), lb1.getTotalCarbonFootprint())) // Sort in descending order
                .collect(Collectors.toList());
    }

//    private LeaderBoard transformToLeaderBoardData(CarbonFootPrint carbonFootPrint) {
//        LeaderBoard dto = new LeaderBoard();
//        dto.setDate(DateConversion(carbonFootPrint.getToday_date()));
//        dto.setTotalCarbonFootprint(carbonFootPrint.getTotal_carbon_footprint());
//        dto.setName(carbonFootPrint.getName()); // Updated field name
//        dto.setCity(carbonFootPrint.getCity());
//        return dto;
//    }
//
//    // Get the leaderboard sorted by totalCarbonFootprint
//    public List<LeaderBoard> getLeaderBoard(String city) {
//        // Fetch all records
//        List<CarbonFootPrint> footprints = carbonTrackRepository.findAllByCity(city);
//
//        // Sort by total_carbon_footprint in descending order and transform to LeaderBoard
//        return footprints.stream()
//                .sorted((fp1, fp2) -> Double.compare(fp2.getTotal_carbon_footprint(), fp1.getTotal_carbon_footprint())) // Sort in descending order
//                .map(this::transformToLeaderBoardData)
//                .collect(Collectors.toList()); // Collect into a list
//    }
}
