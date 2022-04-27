package booking.restaurant.infrastructure.persistence.reservation;

import booking.restaurant.domain.model.Customer;
import booking.restaurant.domain.model.Reservation;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationDTO {

    @Id
    private Long id;
    private String code;
    private Customer customer;
    private LocalDate date;
    private LocalTime time;
    private String note;
    private int persons;
    private int tableNumber;

    public ReservationDTO(Reservation reservation) {
        this.id = reservation.getId();
        this.code = reservation.getCode();
        this.customer = reservation.getCustomer();
        this.date = reservation.getDate();
        this.time = reservation.getTime();
        this.note = reservation.getNote();
        this.persons = reservation.getPersons();
        this.tableNumber = reservation.getTableNumber();
    }

    public Reservation toReservation() {
        return new Reservation(id, code, customer, date, time, note, persons, tableNumber);
    }

}
