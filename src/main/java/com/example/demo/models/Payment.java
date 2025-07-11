package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity

public class Payment extends BaseModel{

    private Date timeOfPayment;
    private double amount;
    private String referenceId;

    @ManyToOne
    private Ticket ticket;

    @Enumerated(EnumType.ORDINAL)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.ORDINAL)
    private PaymentStatus paymentStatus;

}
