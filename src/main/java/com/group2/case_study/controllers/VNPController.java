package com.group2.case_study.controllers;

import com.group2.case_study.config.VNPayService;
import com.group2.case_study.models.Booking;
import com.group2.case_study.models.Flight;
import com.group2.case_study.models.User;
import com.group2.case_study.services.IBookingService;
import com.group2.case_study.services.IFlightService;
import com.group2.case_study.services.IUserService;
import com.group2.case_study.services.impl.MailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Random;

@Controller
@RequestMapping("/vnp")
public class VNPController {

    @Autowired
    private VNPayService vnpService;

    @Autowired
    private MailService mailService;

    @Autowired
    private IFlightService flightService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IBookingService bookingService;


    @PostMapping("")
    public String home(@RequestParam("ticketPrice") String ticketPrice,
                       @RequestParam("fullName") String name,
                       @RequestParam("gender") String gender,
                       @RequestParam("country") String country,
                       @RequestParam("seat") String seat,
                       @RequestParam("flightId") int flightId,
                       @RequestParam("email") String email, Model model){
        model.addAttribute("name", name);
        model.addAttribute("ticketPrice", ticketPrice);
        model.addAttribute("gender", gender);
        model.addAttribute("country", country);
        model.addAttribute("flightId", flightId);
        model.addAttribute("seat", seat);
        model.addAttribute("email", email);
        return "vnp/index";
    }

    @PostMapping("/submitOrder/{flightId}")
    public String submitOrder(@RequestParam("amount") int orderTotal,
                              @RequestParam("orderInfo") String orderInfo,
                              @PathVariable("flightId") int flightId,
                              @RequestParam("gender") String gender,
                              @RequestParam("country") String country,
                              @RequestParam("seat") String seat,
                              @RequestParam("email") String email,
                              HttpServletRequest request, HttpSession session) throws UnsupportedEncodingException {
        session.setAttribute("flightId", flightId);
        session.setAttribute("gender", gender);
        session.setAttribute("country", country);
        session.setAttribute("orderInfo", orderInfo);
        session.setAttribute("seat", seat);
        session.setAttribute("email", email);
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnpService.createOrder(orderTotal, orderInfo, baseUrl);
        return "redirect:" + vnpayUrl;
    }

    @GetMapping("/vnpay-payment")
    public String getPaymentResult(HttpServletRequest request, HttpSession session, Model model){
        int flightId = (int) session.getAttribute("flightId");
        String country = (String) session.getAttribute("country");
        String gender = (String) session.getAttribute("gender");
        String name = (String) session.getAttribute("orderInfo");
        String seat = (String) session.getAttribute("seat");
        String userName = (String)session.getAttribute("userName");
        String email = (String)session.getAttribute("email");
        int paymentStatus = vnpService.orderReturn(request);
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);
        Flight flight = flightService.findById(flightId);
        User user = userService.findByName(userName);
        Booking booking = new Booking();
        String codeBooking = generateRandomCode();
        booking.setCodeBooking(codeBooking);
        booking.setCountry(country);
        booking.setGender(gender);
        booking.setName(name);
        booking.setNumberOfSeats(seat);
        booking.setStatus("Đã thanh toán");
        booking.setTotalPrice(flight.getPrice());
        booking.setFlight(flight);
        booking.setUser(user);
        session.setAttribute("booking", booking);
        if (transactionId != null && transactionId.length() > 1) {
            bookingService.save(booking);
            mailService.sendMail(email, "Spring-email-with-thymeleaf subject", booking);
        }
        return paymentStatus == 1 ? "vnp/ordersuccess" : "vnp/orderfail";
    }

    private String generateRandomCode() {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        Random random = new Random();

        // Tạo phần chữ
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            char letter = letters.charAt(random.nextInt(letters.length()));
            code.append(letter);
        }

        // Tạo phần số
        for (int i = 0; i < 4; i++) {
            char number = numbers.charAt(random.nextInt(numbers.length()));
            code.append(number);
        }

        return code.toString();
    }
}
