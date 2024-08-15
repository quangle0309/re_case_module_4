package com.group2.case_study.repositories;

import com.group2.case_study.models.Airport;
import com.group2.case_study.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IFlightRepository extends JpaRepository<Flight, Integer> {
    @Query("SELECT f FROM Flight f WHERE FUNCTION('DATE', f.departureTime) = :localDate AND f.arrivalAirport.airportId = :arrivalAirportId AND f.departureAirport.airportId = :departureAirportId AND f.departureTime > :currentDateTime")
    List<Flight> findFlights(
            @Param("localDate") LocalDate localDate,
            @Param("arrivalAirportId") Integer arrivalAirportId,
            @Param("departureAirportId") Integer departureAirportId,
            @Param("currentDateTime") LocalDateTime currentDateTime
    );

    @Query("SELECT f FROM Flight f WHERE f.departureTime > :currentDateTime")
    List<Flight> findFlightsAfterCurrentDateTime(
            @Param("currentDateTime") LocalDateTime currentDateTime
    );

    @Query("SELECT DISTINCT a FROM Flight f JOIN Airport a ON f.departureAirport.airportId = a.airportId WHERE f.departureTime > :currentTime")
    List<Airport> findDistinctDepartureAirportsWithFutureFlights(LocalDateTime currentTime);

    @Query("SELECT DISTINCT a FROM Flight f JOIN Airport a ON f.arrivalAirport.airportId = a.airportId " +
            "WHERE f.departureAirport.airportId = :departureAirportId AND f.departureTime > :currentTime")
    List<Airport> findArrivalAirportsByDeparture(Integer departureAirportId, LocalDateTime currentTime);

    @Query("SELECT f FROM Flight f WHERE f.departureAirport.airportId = :departureAirportId " +
            "AND f.arrivalAirport.airportId = :arrivalAirportId " +
            "AND f.departureTime > :currentDateTime")
    List<Flight> findAllFlightByCurrentDateAndAirportId(
            @Param("departureAirportId") Integer departureAirportId,
            @Param("arrivalAirportId") Integer arrivalAirportId,
            @Param("currentDateTime") LocalDateTime currentDateTime
    );
}
