package com.group2.case_study.repositories;

import com.group2.case_study.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAdminFlightRepository extends JpaRepository<Flight, Integer> {
}
