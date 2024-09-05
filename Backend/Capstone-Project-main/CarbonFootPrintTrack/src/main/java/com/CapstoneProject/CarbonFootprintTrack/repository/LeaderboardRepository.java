package com.CapstoneProject.CarbonFootprintTrack.repository;

import com.CapstoneProject.CarbonFootprintTrack.model.leaderBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaderboardRepository extends JpaRepository<leaderBoard,Long> {

    @Query("SELECT l FROM leaderBoard l WHERE l.city = :city ORDER BY l.totalCarbonFootprint DESC")
    List<leaderBoard> findAllByCity(String city);
}
