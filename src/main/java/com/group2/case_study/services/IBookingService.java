package com.group2.case_study.services;

import com.group2.case_study.models.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBookingService {
    Booking findById(int flightId);

    void save(Booking booking);

    Booking findByCode(String booked);

    Page<Booking> getBookingHistoryByUserId(Integer userId, Pageable pageable);
}
