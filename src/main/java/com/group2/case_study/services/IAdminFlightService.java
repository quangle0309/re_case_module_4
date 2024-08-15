package com.group2.case_study.services;

import com.group2.case_study.models.Flight;

import java.util.List;

public interface IAdminFlightService {
    List<Flight> getAllFlights();
    Flight getFlightById(Integer flightId);
    Flight saveFlight(Flight flight);
    void deleteFlight(Integer flightId);
}
