package booking.restaurant.service.repository;

import booking.restaurant.domain.model.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository {

    List<Reservation> findAll();

    List<Reservation> findAllByDateAndTableNumber(LocalDate date, int tableNumber);

    Reservation findByCode(String code);

    Reservation save(Reservation reservation);

    void delete(Reservation reservation);

    List<Reservation> findAllByDate(LocalDate date);

    Reservation findById(Long id);
}
