package com.example.demo.models;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Seat extends BaseModel{

    private String seatNo;

    private int row;
    private int col;

    @Enumerated(EnumType.ORDINAL)
    private ShowSeatState SeatState;

    @Enumerated(EnumType.ORDINAL)
    private SeatType SeatType;
}
