package com.group2.case_study.services;

import com.group2.case_study.models.Airport;
import com.group2.case_study.models.Flight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IFlightService {
    Flight getFlightById(Integer flightId);
    
    Flight findById(int flightId);

    List<Flight> findAllFlights(LocalDate localDate, Integer arrivalAirportId, Integer departureAirportId);

    List<Flight> findAllFlightsByCurrentDateTime();

    List<Airport> getDistinctDepartureAirportsWithFutureFlights();

    List<Airport> getArrivalAirportsByDeparture(Integer departureAirportId, LocalDateTime currentTime);

    List<Flight> findAllFlightsByCurrentDate(Integer departureAirportId, Integer arrivalAirportId);
}
