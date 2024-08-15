package com.group2.case_study.services;

import com.group2.case_study.models.Airport;
import java.util.List;

public interface IAdminAirportService {
    List<Airport> getAllAirports();
    Airport getAirportById(Integer id);
    void saveAirport(Airport airport);
    void deleteAirport(Integer id);
}
