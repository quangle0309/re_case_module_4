package com.group2.case_study.controllers;

import com.group2.case_study.models.Booking;
import com.group2.case_study.models.User;

import com.group2.case_study.services.IBookingService;
import com.group2.case_study.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BookingController {
    @Autowired
    private IBookingService bookingService;

    @Autowired
    private IUserService userService;

    @GetMapping("/booking-history")
    public String viewBookingHistory(Model model, @RequestParam(defaultValue = "0") int page) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.findUserByUsername(username);
        Pageable pageable = PageRequest.of(page, 5);
        Page<Booking> bookings = bookingService.getBookingHistoryByUserId(user.getId(), pageable);
        model.addAttribute("bookings", bookings);
        return "/flight/booking";
    }

}
