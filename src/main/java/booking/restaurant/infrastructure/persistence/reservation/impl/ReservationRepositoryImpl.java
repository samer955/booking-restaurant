package booking.restaurant.infrastructure.persistence.reservation.impl;

import booking.restaurant.domain.model.Reservation;
import booking.restaurant.infrastructure.persistence.reservation.ReservationCrudRepository;
import booking.restaurant.infrastructure.persistence.reservation.ReservationDTO;
import booking.restaurant.service.repository.ReservationRepository;
import org.springframework.stereotype.Component;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationCrudRepository reservationCrudRepository;

    public ReservationRepositoryImpl(ReservationCrudRepository reservationCrudRepository) {
        this.reservationCrudRepository = reservationCrudRepository;
    }

    @Override
    public List<Reservation> findAll() {
        List<ReservationDTO> reservations = reservationCrudRepository.findAll();
        return reservations.stream().map(ReservationDTO::toReservation).collect(Collectors.toList());
    }

    @Override
    public List<Reservation> findAllByDateAndTableNumber(LocalDate date, int tableNumber) {
        List<ReservationDTO> reservations = reservationCrudRepository.findAllByDateAndTableNumber(date, tableNumber);
        return reservations.stream().map(ReservationDTO::toReservation).collect(Collectors.toList());
    }

    @Override
    public Reservation findByCode(String code) {
        ReservationDTO reservationDTO = reservationCrudRepository.findByCode(code);
        return reservationDTO.toReservation();
    }

    @Override
    public Reservation save(Reservation reservation) {
        return reservationCrudRepository.save(new ReservationDTO(reservation)).toReservation();
    }

    @Override
    public void delete(Reservation reservation) {
        reservationCrudRepository.delete(new ReservationDTO(reservation));
    }
}
