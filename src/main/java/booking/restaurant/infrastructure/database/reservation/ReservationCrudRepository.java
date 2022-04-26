package booking.restaurant.infrastructure.database.reservation;

import booking.restaurant.domain.model.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationCrudRepository extends CrudRepository<ReservationDTO, Long> {

    List<ReservationDTO> findAll();
    List<ReservationDTO> findAllByDateAndTableNumber(LocalDate date, int tableNumber);
    ReservationDTO findByCode(String code);

}
