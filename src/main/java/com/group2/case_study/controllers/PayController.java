package com.group2.case_study.controllers;

import com.group2.case_study.models.Flight;
import com.group2.case_study.models.Seat;
import com.group2.case_study.services.IFlightService;
import com.group2.case_study.services.ISeatService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private IFlightService flightService;

    @Autowired
    private ISeatService seatService;

    @PostMapping
    public String showPay(@RequestParam("flightId") int flightId,
                          HttpSession session, Model model) {
        Flight flight = flightService.getFlightById(flightId);
        List<Integer> seatIds = (List<Integer>) session.getAttribute("seats");
        List<Seat> seats = new ArrayList<>();
        for (Integer seatId : seatIds) {
            Seat seat = seatService.findById(seatId);
            seats.add(seat);
        }
        model.addAttribute("flight", flight);
        model.addAttribute("seats", seats);
        return "pay/show-pay";
    }


}
