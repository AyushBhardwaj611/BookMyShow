package com.example.demo.repository;

import com.example.demo.models.ShowSeat;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE) // for update query in dbms
     List<ShowSeat> findAllByIdIn(List<Long> ShowSeatIds);
     // gives a list of seats


  //  List<ShowSeat> findAllById(Iterable<Long> showSeatIds);

    ShowSeat save(ShowSeat showSeat);
}
