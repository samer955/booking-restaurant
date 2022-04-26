package booking.restaurant.domain.service;

import booking.restaurant.domain.model.Reservation;
import booking.restaurant.domain.model.TimeSlot;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TimeSlotsService {

    //TODO: variable should be in the property and then injected
    LocalTime startReservation = LocalTime.of(9, 0);
    LocalTime endReservation = LocalTime.of(22, 0);

    public List<TimeSlot> getFreeTimeSlots(List<Reservation> reservationsOnDay, LocalDate date, int tableNumber) {

        List<TimeSlot> freeTimeSlots = new ArrayList<>();

        List<LocalTime> reservationHours = reservationsOnDay.stream()
                .map(Reservation::getTime).sorted().collect(Collectors.toList());

        if (reservationsOnDay.isEmpty()) {
            return getTimeSlotsFromEmptyList(date, tableNumber, freeTimeSlots);
        }

        if (startReservation.minus(2,ChronoUnit.HOURS).isAfter(startReservation)) {
            getTimeSlotsFromStart(date, tableNumber, freeTimeSlots, reservationHours);
        }

        if(reservationHours.size() - 1 > 0) {
            getTimeSlotsBetweenReservation(date, tableNumber, freeTimeSlots, reservationHours);
        }

        LocalTime last_tmp = reservationHours.get(reservationHours.size() - 1).plus(2, ChronoUnit.HOURS);

        if (last_tmp.isBefore(endReservation)) {
            getTimeSlotsTillEnd(date, tableNumber, freeTimeSlots, last_tmp);
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
    private void getTimeSlotsTillEnd(LocalDate date, int tableNumber, List<TimeSlot> freeTimeSlots, LocalTime last_tmp) {

        while (last_tmp.isBefore(endReservation) || last_tmp.equals(endReservation)) {
            TimeSlot timeslot = new TimeSlot(date, last_tmp, tableNumber);
            freeTimeSlots.add(timeslot);
            last_tmp = last_tmp.plus(15, ChronoUnit.MINUTES);
        }
    }

    //create timeslots from start of the day till the first reservation
    private void getTimeSlotsFromStart(LocalDate date, int tableNumber, List<TimeSlot> freeTimeSlots, List<LocalTime> reservationHours) {
        LocalTime start_tmp = reservationHours.get(0).minus(2, ChronoUnit.HOURS);

        while (start_tmp.isAfter(startReservation) || start_tmp.equals(startReservation)) {
            TimeSlot timeslot = new TimeSlot(date, start_tmp, tableNumber);
            freeTimeSlots.add(timeslot);
            start_tmp = start_tmp.minus(15, ChronoUnit.MINUTES);
        }
    }

    //create all possible timeslots (15 min blocks)
    private List<TimeSlot> getTimeSlotsFromEmptyList(LocalDate date, int tableNumber, List<TimeSlot> freeTimeSlots) {
        
        LocalTime start_tmp = startReservation;

        while (start_tmp.isBefore(endReservation) || start_tmp.equals(endReservation)) {
            TimeSlot timeSlot = new TimeSlot(date, start_tmp, tableNumber);
            freeTimeSlots.add(timeSlot);
            start_tmp = start_tmp.plus(15, ChronoUnit.MINUTES);
        }
        return freeTimeSlots;
    }


}