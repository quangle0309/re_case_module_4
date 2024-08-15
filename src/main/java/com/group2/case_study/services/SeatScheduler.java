package com.group2.case_study.services;

import com.group2.case_study.repositories.ISeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SeatScheduler {
    @Autowired
    private ISeatRepository seatRepository;

    @Scheduled(fixedRate = 60000)
    public void releaseExpiredSeats() {
        LocalDateTime now = LocalDateTime.now();
        seatRepository.updateSeatsToAvailableIfExpired(now);
    }
}
