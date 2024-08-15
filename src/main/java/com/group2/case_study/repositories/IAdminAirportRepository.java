package com.group2.case_study.repositories;

import com.group2.case_study.models.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAdminAirportRepository extends JpaRepository<Airport, Integer> {
}
