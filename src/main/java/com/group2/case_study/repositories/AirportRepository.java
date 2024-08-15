package com.group2.case_study.repositories;

import com.group2.case_study.models.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends JpaRepository<Airport , Integer> {

    @Query("SELECT a FROM Airport a WHERE a.airportCode = :airportCode")
    Airport findByAirportCode(String airportCode);
}
