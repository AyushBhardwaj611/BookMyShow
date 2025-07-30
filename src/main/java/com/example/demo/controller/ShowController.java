package com.example.demo.controller;


import com.example.demo.services.ShowServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.models.Show;
import com.example.demo.dto.TicketDTO.ScheduleShowRequest;
import com.example.demo.services.ShowService;

@RestController
@RequestMapping("/shows")
public class ShowController {

    private ShowService showService;

    public ShowController(ShowServiceImpl showService) {
        this.showService = showService;
    }

    @PostMapping("/schedule")
    public ResponseEntity<Show> scheduleShow(@RequestBody ScheduleShowRequest request) {
        Show scheduledShow = showService.scheduleShow(request);
        return ResponseEntity.ok(scheduledShow);
    }
}
