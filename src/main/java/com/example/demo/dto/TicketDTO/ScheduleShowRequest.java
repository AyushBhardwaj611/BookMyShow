package com.example.demo.dto.TicketDTO;

import com.example.demo.models.Feature;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ScheduleShowRequest {
    private Long movieId;
    private Long auditoriumId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<Feature> features;
}
