package com.group2.case_study.services.impl;

import com.group2.case_study.models.Airport;
import com.group2.case_study.models.Flight;
import com.group2.case_study.repositories.IFlightRepository;
import com.group2.case_study.services.IFlightService;
import com.group2.case_study.services.ISeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FlightService implements IFlightService {

    @Autowired
    private IFlightRepository flightRepository;

    @Autowired
    private ISeatService seatService;

    @Override
    public Flight getFlightById(Integer flightId) {
        return flightRepository.findById(flightId).orElse(null);
    }

    @Override
    public Flight findById(int flightId) {
        return flightRepository.findById(flightId).orElse(null);
    }

    @Override
    public List<Flight> findAllFlights(LocalDate localDate, Integer arrivalAirportId, Integer departureAirportId) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return flightRepository.findFlights(localDate, arrivalAirportId, departureAirportId, currentDateTime);
    }

    @Override
    public List<Flight> findAllFlightsByCurrentDateTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return flightRepository.findFlightsAfterCurrentDateTime(currentDateTime);
    }

    @Override
    public List<Airport> getDistinctDepartureAirportsWithFutureFlights() {
        LocalDateTime currentTime = LocalDateTime.now();
        return flightRepository.findDistinctDepartureAirportsWithFutureFlights(currentTime);
    }


    public List<Airport> getArrivalAirportsByDeparture(Integer departureAirportId, LocalDateTime currentTime) {
        return flightRepository.findArrivalAirportsByDeparture(departureAirportId, currentTime);
    }

    @Override
    public List<Flight> findAllFlightsByCurrentDate(Integer departureAirportId, Integer arrivalAirportId) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return flightRepository.findAllFlightByCurrentDateAndAirportId(departureAirportId, arrivalAirportId, currentDateTime);
    }
}
