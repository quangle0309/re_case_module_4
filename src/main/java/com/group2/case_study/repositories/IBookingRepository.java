package com.group2.case_study.repositories;

import com.group2.case_study.models.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IBookingRepository extends JpaRepository<Booking, Integer> {
    Booking findBookingByCodeBooking(String code);

    Page<Booking> findAllByUserId(Integer userId, Pageable pageable);

}
