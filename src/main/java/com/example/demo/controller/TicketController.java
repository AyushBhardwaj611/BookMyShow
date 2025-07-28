package com.example.demo.controller;

import com.example.demo.dto.TicketDTO.PaymentResponseDTO;
import com.example.demo.dto.TicketDTO.ResponseStatus;
import com.example.demo.dto.TicketDTO.TicketRequestDTO;
import com.example.demo.dto.TicketDTO.TicketResponseDTO;
import com.example.demo.exceptions.SeatNotAvailableException;
import com.example.demo.models.Payment;
import com.example.demo.models.Ticket;
import com.example.demo.services.PaymentService;
import com.example.demo.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController  // we used to make object registries for the dependencies as else the app would start but take time
            // but now spring handles the "spring context" we used to do in the main method
@RequestMapping("/tickets")
public class TicketController {

    private TicketService ticketService;

    private PaymentService paymentService;

    @Autowired
    public TicketController(TicketService ticketService,
                            PaymentService paymentService) {
        this.paymentService = paymentService;
        this.ticketService = ticketService;
    }

    @PostMapping("/book")
    public ResponseEntity<TicketResponseDTO> bookTicket(@RequestBody TicketRequestDTO dto) throws SeatNotAvailableException {
        Ticket ticket = ticketService.bookTicket(dto.getShowId(), dto.getShowSeatIds(), dto.getUserId());
        return ResponseEntity.ok(new TicketResponseDTO(ticket, ResponseStatus.SUCCESS));
    }

    @PostMapping("/{ticketId}/pay")
    public ResponseEntity<PaymentResponseDTO> payForTicket(@PathVariable long ticketId, @RequestParam double amount) {
        Payment payment = paymentService.processPayment(ticketId, amount);
        return ResponseEntity.ok(new PaymentResponseDTO(payment, ResponseStatus.SUCCESS));
    }
}
//  @Autowired --> since we made the service annotated like controller....it will be handled by the
// spring...but now we need to make an object of the service...this can be done by the
// autowired annotation
