package com.example.demo.services;

import com.example.demo.dto.TicketDTO.ScheduleShowRequest;
import com.example.demo.models.Auditorium;
import com.example.demo.models.Movie;
import com.example.demo.models.Show;
import com.example.demo.repository.AuditoriumRepository;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.ShowRepository;
import org.springframework.stereotype.Service;

@Service
public class ShowServiceImpl implements ShowService{

    private ShowRepository showRepository;

    private MovieRepository movieRepository;

    private AuditoriumRepository auditoriumRepository;

    public ShowServiceImpl(ShowRepository showRepository, MovieRepository movieRepository, AuditoriumRepository auditoriumRepository) {
        this.showRepository = showRepository;
        this.movieRepository = movieRepository;
        this.auditoriumRepository = auditoriumRepository;
    }



    @Override
    public Show scheduleShow(ScheduleShowRequest request) {

        if (request == null || request.getMovieId() == null || request.getAuditoriumId() == null ||
                request.getStartTime() == null || request.getEndTime() == null) {
            throw new IllegalArgumentException("Missing required fields in request");
        }

        if (request.getStartTime().isBefore(request.getEndTime()) {
            throw new IllegalArgumentException("End time must be after start time");
        }

        boolean isOverlapping = showRepository.existsByAuditoriumAndTimeRange(
                request.getAuditoriumId(), request.getStartTime(), request.getEndTime());
        if (isOverlapping) {
            throw new IllegalArgumentException("Auditorium is not available for the selected time");
        }

        Movie movie = movieRepository.findById(request.getMovieId()).orElseThrow(() -> new RuntimeException("Movie not found"));



        Auditorium auditorium = auditoriumRepository.findById(request.getAuditoriumId()).orElseThrow(() -> new RuntimeException("Auditorium not found"));

        Show show = new Show();

        show.setAuditorium(auditorium);
        show.setMovie(movie);
        show.setStartTime(request.getStartTime());
        show.setEndTime(request.getEndTime());
        show.setFeatures(request.getFeatures());

        Show savedShow = showRepository.save(show);
        return savedShow;
    }
}
