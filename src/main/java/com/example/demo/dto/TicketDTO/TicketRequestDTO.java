package com.example.demo.dto.TicketDTO;


import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class TicketRequestDTO {

    private List<Long> showSeatIds;
    private Long userId;
    private Long showId;
}
