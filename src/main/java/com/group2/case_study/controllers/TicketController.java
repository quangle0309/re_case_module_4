package com.group2.case_study.controllers;

import com.group2.case_study.models.Booking;
import com.group2.case_study.services.IBookingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TicketController {

    @Autowired
    private IBookingService bookingService;

    @GetMapping("/ticket/{id}")
    public String show(@PathVariable int id, Model model) {
        Booking booking = bookingService.findById(id);
        model.addAttribute("booking", booking);
        return "mail/mail-template";
    }
}
