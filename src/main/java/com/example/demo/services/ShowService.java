package com.example.demo.services;

import com.example.demo.dto.TicketDTO.ScheduleShowRequest;
import com.example.demo.models.Show;


public interface ShowService {
    public Show scheduleShow(ScheduleShowRequest request);
}
