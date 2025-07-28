package com.example.demo.dto.TicketDTO;

import com.example.demo.models.Payment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaymentResponseDTO {
    private Payment payment;
    private ResponseStatus status;
}
