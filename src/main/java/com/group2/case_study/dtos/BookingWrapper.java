package com.group2.case_study.dtos;

import java.util.List;

public class BookingWrapper {
    private List<BookingDto> customers;

    // Getter and Setter
    public List<BookingDto> getCustomers() {
        return customers;
    }

    public void setCustomers(List<BookingDto> customers) {
        this.customers = customers;
    }
}