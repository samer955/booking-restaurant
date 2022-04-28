package booking.restaurant.infrastructure.persistence.reservation;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationCrudRepository extends CrudRepository<ReservationDTO, Long> {

    List<ReservationDTO> findAll();

    List<ReservationDTO> findAllByDateAndTableNumber(LocalDate date, int tableNumber);

    ReservationDTO findByCode(String code);

    List<ReservationDTO> findAllByDate(LocalDate date);

}
