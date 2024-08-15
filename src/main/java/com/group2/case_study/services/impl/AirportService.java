package com.group2.case_study.services.impl;

import com.group2.case_study.models.Airport;
import com.group2.case_study.repositories.AirportRepository;
import com.group2.case_study.services.IAirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportService implements IAirportService {

    @Autowired
    private AirportRepository airportRepository;

    @Override
    public List<Airport> findAll() {
        return airportRepository.findAll();
    }

    @Override
    public Airport findById(Integer AirportId) {
        return airportRepository.findById(AirportId).orElse(null);
    }

    @Override
    public Airport findByAirportCode(String airportCode) {
        return airportRepository.findByAirportCode(airportCode);
    }
}
