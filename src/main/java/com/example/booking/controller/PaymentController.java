package com.example.booking.controller;

import com.example.booking.service.BookingService;
import com.example.booking.service.PaymentService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PaymentController {

    private PaymentService paymentService;

    private BookingService bookingService;

    public void confirmPayment(String user, String bookingId) {
        bookingService.confirmPayment(user, bookingId);
    }

    public void paymentFailed(String user, String bookingId) {
        paymentService.paymentFailed(user, bookingId);
    }

}
