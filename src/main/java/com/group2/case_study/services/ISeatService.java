package com.group2.case_study.services;

import com.group2.case_study.models.Flight;
import com.group2.case_study.models.Seat;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

public interface ISeatService {

    List<List<Seat>> getSeatsGroupedByRows(Integer flightId);

    void updateSeatStatus(List<Integer> seatIds, String status, LocalDateTime holdExpiration);

    void updateSeatStatusConfig(List<Integer> seatIds, String status);

    long countAvailableSeatsByFlightId(Integer flightId);

    Seat findById(Integer seatId);
}
