package booking.restaurant.application;

import booking.restaurant.domain.exception.ReservationException;
import booking.restaurant.domain.model.Customer;
import booking.restaurant.domain.model.Reservation;
import booking.restaurant.domain.model.Table;
import booking.restaurant.domain.model.TimeSlot;
import booking.restaurant.domain.service.TimeSlotsService;
import booking.restaurant.service.repository.ReservationRepository;
import booking.restaurant.service.repository.TableRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static booking.restaurant.domain.exception.ReservationException.*;

@Service
public class BookingService {

    private final ReservationRepository reservationRepository;
    private final TableRepository tableRepository;
    private final TimeSlotsService timeSlotsService;
    private final EmailService emailService;
    private final Clock clock;

    public BookingService(ReservationRepository reservationRepository, TableRepository tableRepository, TimeSlotsService timeSlotsService, EmailService emailService, Clock clock) {
        this.reservationRepository = reservationRepository;
        this.tableRepository = tableRepository;
        this.timeSlotsService = timeSlotsService;
        this.emailService = emailService;
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
                .filter(t -> t.getTime().getHour() >= time.getHour())
                .sorted(Comparator.comparing(TimeSlot::getTime))
                .distinct()
                .limit(4)
                .collect(Collectors.toList());

        return freeTimeSlots;
    }

    public void closeBooking(Customer customer, String note, int persons, TimeSlot timeSlot) {
        Reservation reservation = new Reservation(customer, timeSlot.getDate(), timeSlot.getTime(), note,persons, timeSlot.getTableNumber());
        reservationRepository.save(reservation);
        String confirmBooking = emailService.createConfirmEmail(customer, reservation);

        try {
            emailService.send(customer.email(), confirmBooking);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void cancelReservation(String reservationCode) throws ReservationException {
        Reservation reservation = reservationRepository.findByCode(reservationCode);
        if(reservation == null) {
            throw new ReservationException(ERROR_MSG_WRONG_CODE);
        }
        if(LocalDateTime.now(clock).until(LocalDateTime.of(reservation.getDate(), reservation.getTime()), ChronoUnit.HOURS) < 3) {
            throw new ReservationException(ERROR_MSG_CANCEL_BOOKING);
        }
        reservationRepository.delete(reservation);

        String cancelBooking = emailService.createCancelEmail(reservation.getCustomer(),reservation);
        try {
            emailService.send(reservation.getCustomer().email(), cancelBooking);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public List<Reservation> getAllReservationsByDate(LocalDate date) {
        return reservationRepository.findAllByDate(date);
    }

    @Secured("ROLE_ADMIN")
    public void cancelReservationAdmin(Long id) {
        Reservation reservation = reservationRepository.findById(id);
        if(reservation != null) {
            reservationRepository.delete(reservation);
        }
    }

    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public Reservation findById(Long id) {
        return reservationRepository.findById(id);
    }
}
