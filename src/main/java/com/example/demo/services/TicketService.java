package com.example.demo.services;


import com.example.demo.exceptions.SeatNotAvailableException;
import com.example.demo.models.*;
import com.example.demo.repository.ShowSeatRepository;
import com.example.demo.repository.TicketRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private ShowSeatRepository showSeatRepository;
    private UserRepository userRepository;
    private TicketRepository ticketRepository;

    @Autowired
    public TicketService(ShowSeatRepository showSeatRepository,
                         UserRepository userRepository,
                         TicketRepository ticketRepository) {
        this.showSeatRepository = showSeatRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Ticket bookTicket(Long showId, List<Long> showSeatIds, Long userId) throws SeatNotAvailableException {
        List<ShowSeat> showSeats = showSeatRepository.findAllByIdIn(showSeatIds);

        for (ShowSeat s : showSeats) {
            if (!s.getShowSeatState().equals(ShowSeatState.AVAILABLE)) {
                throw new SeatNotAvailableException();
            }
        }

        /*
        * Locks
        * taking the lock by updating the status and saving in db
        *         w1 : allow the db to take locks
*                 w2 : handle the locking on the application -->
*
* if we want to remove the locks than we can make a payment controller that deals with
* ticket searvice and removes the locks in every 15 mins if the pay is not done

         */

        for (ShowSeat s : showSeats) {
            s.getShowSeatState().equals(ShowSeatState.LOCKED);
            showSeatRepository.save(s);
        }

        Ticket ticket= new Ticket();
        Optional<User> user = userRepository.findById(userId);  // fetch the user

        ticket.setBookedBy(user.get());  // get the user
        ticket.setTicketStatus(TicketStatus.PENDING);
        ticket.setShowSeats(showSeats);

        return ticketRepository.save(ticket);
    }
}
