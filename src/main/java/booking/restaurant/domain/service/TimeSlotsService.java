package booking.restaurant.domain.service;

import booking.restaurant.domain.ReservationTime;
import booking.restaurant.domain.model.Reservation;
import booking.restaurant.domain.model.TimeSlot;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TimeSlotsService {

    private final ReservationTime reservationTime;

    public TimeSlotsService(ReservationTime reservationTime) {
        this.reservationTime = reservationTime;
    }

    public List<TimeSlot> getFreeTimeSlots(List<Reservation> reservationsOnDay, LocalDate date, int tableNumber) {

        List<TimeSlot> freeTimeSlots = new ArrayList<>();

        List<LocalTime> reservationHours = reservationsOnDay.stream()
                .map(Reservation::getTime).sorted().collect(Collectors.toList());

        if (reservationsOnDay.isEmpty()) {
            return getTimeSlotsFromEmptyList(date, tableNumber, freeTimeSlots);
        }

        if (reservationHours.get(0).minus(2,ChronoUnit.HOURS).isAfter(reservationTime.start())) {
            getTimeSlotsFromStart(date, tableNumber, freeTimeSlots, reservationHours);
        }

        if(reservationHours.size() - 1 > 0) {
            getTimeSlotsBetweenReservation(date, tableNumber, freeTimeSlots, reservationHours);
        }

        LocalTime last_tmp = reservationHours.get(reservationHours.size() - 1);
        LocalDateTime dateTime_last = LocalDateTime.of(date,last_tmp).plus(2,ChronoUnit.HOURS);
        LocalDateTime endDateTime = LocalDateTime.of(date,reservationTime.end());

        //last_temp-1 mint
        if (dateTime_last.isBefore(endDateTime) || dateTime_last.equals(endDateTime)) {
            getTimeSlotsTillEnd(date, tableNumber, freeTimeSlots, last_tmp.plus(2,ChronoUnit.HOURS));
        }

        return freeTimeSlots;
    }

    //create free timeslots between one or more reservation (15 min blocks)
    private void getTimeSlotsBetweenReservation(LocalDate date, int tableNumber, List<TimeSlot> freeTimeSlots, List<LocalTime> reservationHours) {
        int i = 0;
        while (i < reservationHours.size() - 1) {

            LocalTime start_temp = reservationHours.get(i).plus(2, ChronoUnit.HOURS);
            LocalTime end_temp = reservationHours.get(i + 1).minus(2, ChronoUnit.HOURS);

            if (start_temp.isBefore(end_temp) || start_temp.equals(end_temp)) {
                while (start_temp.isBefore(end_temp) || start_temp.equals(end_temp)) {
                    TimeSlot timeSlot = new TimeSlot(date, start_temp, tableNumber);
                    freeTimeSlots.add(timeSlot);
                    start_temp = start_temp.plus(15, ChronoUnit.MINUTES);
                }
            }
            i++;
        }
    }

    //create TimeSlots from last reservation till the end of the day
    public void getTimeSlotsTillEnd(LocalDate date, int tableNumber, List<TimeSlot> freeTimeSlots, LocalTime last_tmp) {
        while (last_tmp.isBefore(reservationTime.end()) ||  last_tmp.equals(reservationTime.end())) {
            TimeSlot timeslot = new TimeSlot(date, last_tmp, tableNumber);
            freeTimeSlots.add(timeslot);
            last_tmp = last_tmp.plus(15, ChronoUnit.MINUTES);
        }
    }

    //create timeslots from start of the day till the first reservation
    private void getTimeSlotsFromStart(LocalDate date, int tableNumber, List<TimeSlot> freeTimeSlots, List<LocalTime> reservationHours) {
        LocalTime start_tmp = reservationHours.get(0).minus(2, ChronoUnit.HOURS);

        while (start_tmp.isAfter(reservationTime.start()) || start_tmp.equals(reservationTime.start())) {
            TimeSlot timeslot = new TimeSlot(date, start_tmp, tableNumber);
            freeTimeSlots.add(timeslot);
            start_tmp = start_tmp.minus(15, ChronoUnit.MINUTES);
        }
    }

    //create all possible timeslots (15 min blocks)
    private List<TimeSlot> getTimeSlotsFromEmptyList(LocalDate date, int tableNumber, List<TimeSlot> freeTimeSlots) {
        
        LocalTime start_tmp = reservationTime.start();

        while (start_tmp.isBefore(reservationTime.end()) || start_tmp.equals(reservationTime.end())) {
            TimeSlot timeSlot = new TimeSlot(date, start_tmp, tableNumber);
            freeTimeSlots.add(timeSlot);
            start_tmp = start_tmp.plus(15, ChronoUnit.MINUTES);
        }
        return freeTimeSlots;
    }


}
