package com.group2.case_study.controllers;

import com.group2.case_study.models.Airport;
import com.group2.case_study.services.impl.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class AirportController {
    @Autowired
    private FlightService flightService;

    @GetMapping("/api/airports/arrival")
    public ResponseEntity<List<Airport>> getArrivalAirports(@RequestParam Integer departureAirportId) {
        LocalDateTime currentTime = LocalDateTime.now();
        List<Airport> airports = flightService.getArrivalAirportsByDeparture(departureAirportId, currentTime);
        if (airports.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(airports, HttpStatus.OK);
    }
}
