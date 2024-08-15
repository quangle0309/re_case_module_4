package com.group2.case_study.services.impl;

import com.group2.case_study.models.Airport;
import com.group2.case_study.repositories.IAdminAirportRepository;
import com.group2.case_study.services.IAdminAirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdminAirportService implements IAdminAirportService {

    @Autowired
    private IAdminAirportRepository adminAirportRepository;

    @Override
    public List<Airport> getAllAirports() {
        return adminAirportRepository.findAll();
    }

    @Override
    public Airport getAirportById(Integer id) {
        return adminAirportRepository.findById(id).orElse(null);
    }

    @Override
    public void saveAirport(Airport airport) {
        adminAirportRepository.save(airport);
    }

    @Override
    public void deleteAirport(Integer id) {
        adminAirportRepository.deleteById(id);
    }
}
