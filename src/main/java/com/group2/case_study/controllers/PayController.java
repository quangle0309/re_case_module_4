package com.group2.case_study.controllers;

import com.group2.case_study.models.Flight;
import com.group2.case_study.services.IFlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private IFlightService flightService;

    @PostMapping
    public String showPay(@RequestParam("flightId") int flightId,
                          @RequestParam("seat") String seat,Model model) {
        Flight flight = flightService.getFlightById(flightId);
        model.addAttribute("flight", flight);
        model.addAttribute("seat", seat);
        return "pay/show-pay";
    }


}
