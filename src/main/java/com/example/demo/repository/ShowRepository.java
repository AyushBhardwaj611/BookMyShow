package com.example.demo.repository;

import com.example.demo.models.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ShowRepository extends JpaRepository<Show, Long> {

    // In ShowRepository.java
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Show s WHERE s.auditorium.id = :auditoriumId AND ((s.startTime < :endTime) AND (s.endTime > :startTime))")
    boolean existsByAuditoriumAndTimeRange(@Param("auditoriumId") Long auditoriumId, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}
