package com.example.demo.controller;

import com.example.demo.dto.TicketDTO.ResponseStatus;
import com.example.demo.dto.TicketDTO.TicketRequestDTO;
import com.example.demo.dto.TicketDTO.TicketResponseDTO;
import com.example.demo.exceptions.SeatNotAvailableException;
import com.example.demo.models.Ticket;
import com.example.demo.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller  // we used to make object registries for the dependencies as else the app would start but take time
            // but now spring handles the "spring context" we used to do in the main method
public class TicketController {

    private TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public TicketResponseDTO bookTicket(TicketRequestDTO dto) throws SeatNotAvailableException {
        Ticket ticket = ticketService.bookTicket(dto.getShowId(), dto.getShowSeatIds(), dto.getUserId());
        return new TicketResponseDTO(ticket, ResponseStatus.SUCCESS);
    }
}

//  @Autowired --> since we made the service annotated like controller....it will be handled by the
// spring...but now we need to make an object of the service...this can be done by the
// autowired annotation
