package booking.restaurant.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class TimeSlot {

    private LocalDate date;
    private LocalTime time;
    private int tableNumber;

    public TimeSlot(LocalDate date, LocalTime time, int tableNumber) {
        this.date = date;
        this.time = time;
        this.tableNumber = tableNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeSlot timeSlot = (TimeSlot) o;
        return Objects.equals(date, timeSlot.date) && Objects.equals(time, timeSlot.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time);
    }

    @Override
    public String toString() {
        return "TimeSlot{" +
                "date=" + date +
                ", time=" + time +
                ", tableNumber=" + tableNumber +
                '}';
    }
}
