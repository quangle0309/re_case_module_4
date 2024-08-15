package com.group2.case_study.services.impl;

import com.group2.case_study.models.Flight;
import com.group2.case_study.repositories.IAdminFlightRepository;
import com.group2.case_study.repositories.ISeatRepository;
import com.group2.case_study.services.IAdminFlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminFlightService implements IAdminFlightService {

    @Autowired
    private IAdminFlightRepository adminFlightRepository;

    @Autowired
    private ISeatRepository seatRepository;

    @Override
    public List<Flight> getAllFlights() {
        return adminFlightRepository.findAll();
    }

    @Override
    public Flight getFlightById(Integer flightId) {
        return adminFlightRepository.findById(flightId).orElse(null);
    }

    @Override
    public Flight saveFlight(Flight flight) {
        return adminFlightRepository.save(flight);
    }


    @Transactional
    @Override
    public void deleteFlight(Integer flightId) {
        seatRepository.deleteAllByFlight_FlightId(flightId);
        adminFlightRepository.deleteById(flightId);
    }
}
