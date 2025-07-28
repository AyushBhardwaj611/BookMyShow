package com.example.demo.services;

import com.example.demo.models.Payment;
import com.example.demo.models.PaymentStatus;
import com.example.demo.models.Ticket;
import com.example.demo.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class PaymentService {

    private PaymentRepository paymentRepository;

    private TicketService ticketService;

    public PaymentService(PaymentRepository paymentRepository, TicketService ticketService) {
        this.paymentRepository = paymentRepository;
        this.ticketService = ticketService;
    }

    public Payment processPayment(Long ticketId, double amount) {
        // Validate ticket and amount
        if (ticketId == null || amount <= 0) {
            throw new IllegalArgumentException("Invalid ticket ID or amount");
        }

        Ticket ticket = ticketService.confirmTicket(ticketId);


        // Process payment logic (e.g., call payment gateway)
        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setTimeOfPayment(Date.from(Instant.now()));
        payment.setPaymentStatus(PaymentStatus.SUCCESS); // Assume payment is successful
        payment.setTicket(ticket);

        // Save payment to the repository
          paymentRepository.save(payment);

          return payment;

    }
}
