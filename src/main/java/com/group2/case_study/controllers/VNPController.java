package com.group2.case_study.controllers;

import com.group2.case_study.config.VNPayService;
import com.group2.case_study.dtos.BookingDto;
import com.group2.case_study.dtos.BookingWrapper;
import com.group2.case_study.models.Booking;
import com.group2.case_study.models.Flight;
import com.group2.case_study.models.User;
import com.group2.case_study.services.IBookingService;
import com.group2.case_study.services.IFlightService;
import com.group2.case_study.services.ISeatService;
import com.group2.case_study.services.IUserService;
import com.group2.case_study.services.impl.MailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
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

    @Autowired
    private ISeatService seatService;

    private static final String PAYMENT_IDENTIFIER = "PENDING";

    @PostMapping("")
    public String home(@RequestParam("ticketPrice") double ticketPrice,
                       @RequestParam("flightId") int flightId,
                       @ModelAttribute BookingWrapper bookingWrapper,
                       HttpSession session,
                       Model model) {
        if (session.getAttribute(PAYMENT_IDENTIFIER) != null) {
            return "pay/error";
        }


        List<BookingDto> customers = bookingWrapper.getCustomers();
        List<BookingDto> customersNew = new ArrayList<>();
        for (BookingDto customer : customers) {
            if (customer.getName() == null) {
                continue;
            }
            customersNew.add(customer);
        }
        session.setAttribute("bookingWrappers", customersNew);
        double totalPrice = customersNew.size() * ticketPrice;
        model.addAttribute("ticketPrice", totalPrice);
        model.addAttribute("flightId", flightId);
        model.addAttribute("customers", customersNew);

        // Gán định danh thanh toán vào phiên
        session.setAttribute(PAYMENT_IDENTIFIER, generateRandomCode());

        return "vnp/index";
    }

    @PostMapping("/submitOrder/{flightId}")
    public String submitOrder(@RequestParam("amount") double orderTotal,
                              @RequestParam("orderInfo") String orderInfo,
                              @PathVariable("flightId") int flightId,
                              HttpServletRequest request,
                              HttpSession session) throws UnsupportedEncodingException {
        // Kiểm tra xem thanh toán đã đang được tiến hành chưa
        if (session.getAttribute(PAYMENT_IDENTIFIER) == null) {
            return "pay/error";
        }

        int orderTotalI = (int) orderTotal;
        session.setAttribute("flightId", flightId);
        session.setAttribute("orderInfo", orderInfo);

        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnpService.createOrder(orderTotalI, orderInfo, baseUrl);

        return "redirect:" + vnpayUrl;
    }

    @GetMapping("/vnpay-payment")
    public String getPaymentResult(HttpServletRequest request,
                                   HttpSession session,
                                   Model model) {
        // Kiểm tra xem thanh toán đã đang được tiến hành chưa
        if (session.getAttribute(PAYMENT_IDENTIFIER) == null) {
            return "pay/error";
        }
        List<BookingDto> customers = (List<BookingDto>) session.getAttribute("bookingWrappers");
        if (customers == null) {
            model.addAttribute("errorMessage", "Danh sách khách hàng không được cung cấp.");
            return "vnp/orderfail";
        }

        int flightId = (int) session.getAttribute("flightId");
        String userName = (String) session.getAttribute("userName");
        int paymentStatus = vnpService.orderReturn(request);
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");
        List<Integer> seatIds = (List<Integer>) session.getAttribute("seats");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        Flight flight = flightService.findById(flightId);
        User user = userService.findByName(userName);
        String email = user.getEmail();
        List<Booking> bookings = new ArrayList<>();

        for (BookingDto bookingDto : customers) {
            Booking booking = new Booking();
            String codeBooking = generateRandomCode();
            booking.setCodeBooking(codeBooking);
            booking.setStatus("Đã thanh toán");
            booking.setTotalPrice(flight.getPrice());
            booking.setFlight(flight);
            booking.setUser(user);
            BeanUtils.copyProperties(bookingDto, booking);
            bookings.add(booking);
        }

        session.setAttribute("booking", bookings);

        if (transactionId != null && transactionId.length() > 1) {
            for (Booking booking : bookings) {
                bookingService.save(booking);
                mailService.sendMail(email, "Spring-email-with-thymeleaf subject", booking);
            }
            seatService.updateSeatStatusConfig(seatIds, "UNAVAILABLE");
        }

        // Xóa thanh toán sau khi hoàn tất
        session.removeAttribute(PAYMENT_IDENTIFIER);
        return paymentStatus == 1 ? "vnp/ordersuccess" : "vnp/orderfail";
    }

    private String generateRandomCode() {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            char letter = letters.charAt(random.nextInt(letters.length()));
            code.append(letter);
        }
        for (int i = 0; i < 4; i++) {
            char number = numbers.charAt(random.nextInt(numbers.length()));
            code.append(number);
        }
        return code.toString();
    }
}
