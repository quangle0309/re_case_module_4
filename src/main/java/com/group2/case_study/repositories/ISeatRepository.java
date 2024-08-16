package com.group2.case_study.repositories;

import com.group2.case_study.models.Flight;
import com.group2.case_study.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ISeatRepository extends JpaRepository<Seat, Integer> {

    @Query("SELECT s FROM Seat s WHERE s.flight.flightId = :flightId")
    List<Seat> findSeatsByFlightId(@Param("flightId") Integer flightId);

    @Modifying
    @Transactional
    @Query("UPDATE Seat s SET s.availabilityStatus = :status, s.holdExpiration = :holdExpiration WHERE s.seatId = :seatId")
    void updateSeatStatus(Integer seatId, String status, LocalDateTime holdExpiration);

    @Modifying
    @Transactional
    @Query("UPDATE Seat s SET s.availabilityStatus = :status WHERE s.seatId = :seatId")
    void updateSeatStatusConfig(Integer seatId, String status);

    @Modifying
    @Transactional
    @Query("UPDATE Seat s SET s.availabilityStatus = 'AVAILABLE', s.holdExpiration = NULL WHERE s.availabilityStatus = 'HOLD' AND s.holdExpiration < :now")
    void updateSeatsToAvailableIfExpired(LocalDateTime now);

    @Query("SELECT COUNT(s) FROM Seat s WHERE s.flight.flightId = :flightId AND s.availabilityStatus = 'AVAILABLE'")
    long countAvailableSeatsByFlightId(Integer flightId);

    @Modifying
    @Transactional
    void deleteAllByFlight_FlightId(Integer flightId);


}
