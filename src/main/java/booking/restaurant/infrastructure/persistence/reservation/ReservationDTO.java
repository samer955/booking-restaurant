package booking.restaurant.infrastructure.persistence.reservation;

import booking.restaurant.domain.model.Customer;
import booking.restaurant.domain.model.Reservation;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

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

    @PersistenceConstructor
    public ReservationDTO(Long id, String code, Customer customer, LocalDate date, LocalTime time, String note, int persons, int tableNumber) {
        this.id = id;
        this.code = code;
        this.customer = customer;
        this.date = date;
        this.time = time;
        this.note = note;
        this.persons = persons;
        this.tableNumber = tableNumber;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationDTO that = (ReservationDTO) o;
        return persons == that.persons && tableNumber == that.tableNumber && Objects.equals(id, that.id) && Objects.equals(code, that.code) && Objects.equals(customer, that.customer) && Objects.equals(date, that.date) && Objects.equals(time, that.time) && Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, customer, date, time, note, persons, tableNumber);
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getNote() {
        return note;
    }

    public int getPersons() {
        return persons;
    }

    public int getTableNumber() {
        return tableNumber;
    }
}
