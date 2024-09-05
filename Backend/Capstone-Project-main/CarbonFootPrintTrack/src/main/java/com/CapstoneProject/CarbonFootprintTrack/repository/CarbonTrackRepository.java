package com.CapstoneProject.CarbonFootprintTrack.repository;

import com.CapstoneProject.CarbonFootprintTrack.model.CarbonFootPrint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarbonTrackRepository extends JpaRepository<CarbonFootPrint,Long> {
    List<CarbonFootPrint> findByUserId(Long id);

   // @Query("SELECT c FROM CarbonFootPrint c WHERE c.city = :city AND c.today_date = (SELECT MAX(cf.today_date) FROM CarbonFootPrint cf WHERE cf.userId = c.userId) ORDER BY c.userId ASC")
   // @Query("SELECT c FROM CarbonFootPrint c WHERE c.city = :city")
    //List<CarbonFootPrint> findAllByCity(String city);

    //List<CarbonFootPrint> findAllByOrderByCarbon_footprintDesc();
}
