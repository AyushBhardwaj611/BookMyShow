package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity

public class Ticket extends BaseModel{

    @ManyToOne
    private User bookedBy;

    @ManyToOne
    private Show show;

    private Date timeOfBooking;
    private double totalAmount;

    @OneToMany
    private List<ShowSeat> showSeats;

    @Enumerated(EnumType.ORDINAL)
    private TicketStatus ticketStatus;

    @OneToMany
    private List<Payment> payments;

}
