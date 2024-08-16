package com.group2.case_study.services.impl;

import com.group2.case_study.models.Seat;
import com.group2.case_study.repositories.ISeatRepository;
import com.group2.case_study.services.ISeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SeatService implements ISeatService {
    @Autowired
    private ISeatRepository seatRepository;

    @Override
    public List<List<Seat>> getSeatsGroupedByRows(Integer flightId) {
        List<Seat> seats = seatRepository.findSeatsByFlightId(flightId);

        List<List<Seat>> seatRows = new ArrayList<>();
        for (int i = 0; i < seats.size(); i += 6) {
            seatRows.add(seats.subList(i, Math.min(i + 6, seats.size())));
        }

        return seatRows;
    }

    @Override
    @Transactional
    public void updateSeatStatus(List<Integer> seatIds, String status, LocalDateTime holdExpiration) {
        for (Integer seatId : seatIds) {
            seatRepository.updateSeatStatus(seatId, status, holdExpiration);
        }
    }

    @Override
    public void updateSeatStatusConfig(List<Integer> seatIds, String status) {
        for (Integer seatId : seatIds) {
            seatRepository.updateSeatStatusConfig(seatId, status);
        }
    }


    @Override
    public long countAvailableSeatsByFlightId(Integer flightId) {
        return seatRepository.countAvailableSeatsByFlightId(flightId);
    }

    @Override
    public Seat findById(Integer seatId) {
        return seatRepository.findById(seatId).orElse(null);
    }
}
