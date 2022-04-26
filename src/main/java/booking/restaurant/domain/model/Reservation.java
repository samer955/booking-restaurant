package booking.restaurant.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class Reservation {

    private Long id;
    private String code;
    private Customer customer;
    private LocalDate date;
    private LocalTime time;
    private String note;
    private int persons;
    private int tableNumber;

    public Reservation(Customer customer, LocalDate date, LocalTime time, String note, int persons, int tableNumber) {
        this.id = null;
        this.code = UUID.randomUUID().toString();
        this.customer = customer;
        this.date = date;
        this.time = time;
        this.note = note;
        this.persons = persons;
        this.tableNumber = tableNumber;
    }

    public Reservation(Long id, String code, Customer customer, LocalDate date,
                       LocalTime time, String note, int persons, int tableNumber) {
        this.id = id;
        this.code = code;
        this.customer = customer;
        this.date = date;
        this.time = time;
        this.note = note;
        this.persons = persons;
        this.tableNumber = tableNumber;
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
