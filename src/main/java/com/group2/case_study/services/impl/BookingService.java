package com.group2.case_study.services.impl;

import com.group2.case_study.models.Booking;
import com.group2.case_study.repositories.IBookingRepository;
import com.group2.case_study.services.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookingService implements IBookingService {
    @Autowired
    private IBookingRepository bookingRepository;
    @Override
    public Booking findById(int flightId) {
        return bookingRepository.findById(flightId).get();
    }

    @Override
    public void save(Booking booking) {
        bookingRepository.save(booking);
    }

    @Override
    public Booking findByCode(String booked) {
        return bookingRepository.findBookingByCodeBooking(booked);
    }

    @Override
    public Page<Booking> getBookingHistoryByUserId(Integer userId, Pageable pageable) {
        return bookingRepository.findAllByUserId(userId, pageable);
    }
}
