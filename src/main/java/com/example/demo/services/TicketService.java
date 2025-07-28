package com.example.demo.services;

import com.example.demo.exceptions.SeatNotAvailableException;
import com.example.demo.models.*;
import com.example.demo.repository.ShowSeatRepository;
import com.example.demo.repository.TicketRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
* ticket service and removes the locks in every 15 mins if the pay is not done
         */

        for (ShowSeat s : showSeats) {
            s.setShowSeatState(ShowSeatState.LOCKED);
            showSeatRepository.save(s);
        }

        Ticket ticket= new Ticket();
        Optional<User> user = userRepository.findById(userId);  // fetch the user

        if (user.isEmpty()) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        ticket.setBookedBy(user.get());  // get the user
        ticket.setTicketStatus(TicketStatus.PENDING);
        ticket.setShowSeats(showSeats);
        ticket.setCreatedAt(Instant.now());

        return ticketRepository.save(ticket);
    }


    @Scheduled(fixedRate = 60000) // runs every minute automatically by springboot
    public void releaseExpiredLocks() {
        List<Ticket> pendingTickets = ticketRepository.findAllByTicketStatus(TicketStatus.PENDING);
        for (Ticket ticket : pendingTickets) {
            if (ticket.getCreatedAt().isBefore(Instant.now().minus(15, ChronoUnit.MINUTES))) {
                for (ShowSeat seat : ticket.getShowSeats()) {
                    seat.setShowSeatState(ShowSeatState.AVAILABLE);
                    showSeatRepository.save(seat);
                }
                ticket.setTicketStatus(TicketStatus.CANCELLED);
                ticketRepository.save(ticket);
            }
        }
    }

    /*
     * This method confirms the ticket by changing its status to SUCCESS.
     * It is assumed that the payment has been successfully processed before calling this method.
     *
     * @param ticketId the ID of the ticket to confirm
     * @return the confirmed Ticket object
     */
    @Transactional
    public Ticket confirmTicket(Long ticketId) {
        Optional<Ticket> ticketOpt = ticketRepository.findById(ticketId);
        if (ticketOpt.isEmpty()) {
            throw new RuntimeException("Ticket not found with id: " + ticketId);
        }
        Ticket ticket = ticketOpt.get();
        ticket.setTicketStatus(TicketStatus.SUCCESS);
        return ticketRepository.save(ticket);
    }


}
