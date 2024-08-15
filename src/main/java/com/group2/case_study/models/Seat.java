package com.group2.case_study.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table (name = "seats")
@Data
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seatId;

    @ManyToOne
    @JoinColumn(name = "flightId", nullable = false)
    private Flight flight;

    @Column(nullable = false, length = 10)
    private String seatNumber;

    @Column(nullable = false, length = 50)
    private String classType;

    @Column(nullable = false)
    private String availabilityStatus;

    @Column(nullable = true)
    private LocalDateTime holdExpiration;
}
