package booking.restaurant.service;

import booking.restaurant.domain.exception.ReservationException;
import booking.restaurant.domain.model.Customer;
import booking.restaurant.domain.model.Reservation;
import booking.restaurant.domain.model.Table;
import booking.restaurant.domain.model.TimeSlot;
import booking.restaurant.domain.service.TimeSlotsService;
import booking.restaurant.service.repository.ReservationRepository;
import booking.restaurant.service.repository.TableRepository;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static booking.restaurant.domain.exception.ReservationException.ERR_MSG_DATE_OR_TIME_NOT_CORRECT;

@Service
public class BookingService {

    private final ReservationRepository reservationRepository;
    private final TableRepository tableRepository;
    private final TimeSlotsService timeSlotsService;
    private final Clock clock;

    public BookingService(ReservationRepository reservationRepository, TableRepository tableRepository, TimeSlotsService timeSlotsService, Clock clock) {
        this.reservationRepository = reservationRepository;
        this.tableRepository = tableRepository;
        this.timeSlotsService = timeSlotsService;
        this.clock = clock;
    }

    public List<TimeSlot> findFreeTable(int persons, LocalDate date, LocalTime time) throws ReservationException {

        if(date.isBefore(LocalDate.now(clock)) ||
                (date.isEqual(LocalDate.now(clock)) && time.isBefore(LocalTime.now(clock)))) {
            throw new ReservationException(ERR_MSG_DATE_OR_TIME_NOT_CORRECT);
        }

        //find table with enouth capacity
        List<Table> tablesWithCapacity = tableRepository.findAllByMinOrMaxCapacity(persons, persons);

        //get all the free TimeSlots for every table and sort them by Time
        List<TimeSlot> freeTimeSlots = tablesWithCapacity.stream()
                .map(table -> timeSlotsService.getFreeTimeSlots(reservationRepository.findAllByDateAndTableNumber(date, table.getNumber()), date, table.getNumber()))
                .flatMap(List::stream)
                .sorted(Comparator.comparing(TimeSlot::getTime))
                .distinct()
                .filter(t -> t.getTime().getHour() >= time.getHour())
                .filter(t -> t.getTime().getMinute() >= time.getMinute())
                .limit(4)
                .collect(Collectors.toList());

        return freeTimeSlots;
    }

    public void closeBooking(Customer customer, String note, int persons, TimeSlot timeSlot) {
        Reservation reservation = new Reservation(customer, timeSlot.getDate(), timeSlot.getTime(), note,persons, timeSlot.getTableNumber());
        reservationRepository.save(reservation);
    }
}
