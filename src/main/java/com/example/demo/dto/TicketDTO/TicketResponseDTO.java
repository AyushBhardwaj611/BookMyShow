package com.example.demo.dto.TicketDTO;

import com.example.demo.models.Ticket;
import lombok.AllArgsConstructor;

import java.util.List;
@AllArgsConstructor
public class TicketResponseDTO {

private Ticket ticket;
private ResponseStatus responseStatus;

}
