package booking.restaurant.domain;

import booking.restaurant.TestHelper;
import booking.restaurant.domain.model.Reservation;
import booking.restaurant.domain.model.TimeSlot;
import booking.restaurant.domain.service.TimeSlotsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static booking.restaurant.TestHelper.*;
import static org.assertj.core.api.Assertions.assertThat;

public class TimeSlotServiceTest {

    ReservationTime reservationTime = new ReservationTime(START_RESERVATION,END_RESERVATION);
    TimeSlotsService timeSlotsService = new TimeSlotsService(reservationTime);


    @Test
    void test_1() {
        assertThat(timeSlotsService).isNotNull();
    }

    @Test
    @DisplayName("No reservation for the whole Day in one table so all timeslots are free")
    void test_2() {
        List<Reservation> reservationsOnDay = new ArrayList<>();
        int tableNumber = 4;

        List<TimeSlot> timeSlots = timeSlotsService.getFreeTimeSlots(reservationsOnDay,DATE_OF_2022_05_20,tableNumber);

        assertThat(timeSlots).hasSize(53);
    }

    @Test
    @DisplayName("Table booked for the whole day, no timeslots free")
    void test_3() {

        int tableNumber = 4;

        List<Reservation> reservationsOnDay = List.of(
                RES_OF_2022_05_20_AT_09_00,
                RES_OF_2022_05_20_AT_11_00,
                RES_OF_2022_05_20_AT_13_00,
                RES_OF_2022_05_20_AT_15_00,
                RES_OF_2022_05_20_AT_17_00,
                RES_OF_2022_05_20_AT_19_00,
                RES_OF_2022_05_20_AT_21_00
        );

        List<TimeSlot> timeSlots = timeSlotsService.getFreeTimeSlots(reservationsOnDay,DATE_OF_2022_05_20,tableNumber);

        assertThat(timeSlots).hasSize(0);
    }

    @Test
    @DisplayName("Reservation start at 13:00 till end, so we have free timeslots from 9:00 to 11:00")
    void test_4() {

        int tableNumber = 4;

        List<Reservation> reservationsOnDay = List.of(
                RES_OF_2022_05_20_AT_13_00,
                RES_OF_2022_05_20_AT_15_00,
                RES_OF_2022_05_20_AT_17_00,
                RES_OF_2022_05_20_AT_19_00,
                RES_OF_2022_05_20_AT_21_00
        );

        List<TimeSlot> timeSlots = timeSlotsService.getFreeTimeSlots(reservationsOnDay,DATE_OF_2022_05_20,tableNumber);

        assertThat(timeSlots).hasSize(9);
        assertThat(timeSlots).contains(TIME_SLOT_OF_2022_05_20_AT_09_00);
        assertThat(timeSlots).contains(TIME_SLOT_OF_2022_05_20_AT_11_00);

    }

    @Test
    @DisplayName("Reservation start at 9:00 till 17, so we have free timeslots from 19:00 to 22:00")
    void test_5() {

        int tableNumber = 4;

        List<Reservation> reservationsOnDay = List.of(
                RES_OF_2022_05_20_AT_09_00,
                RES_OF_2022_05_20_AT_11_00,
                RES_OF_2022_05_20_AT_13_00,
                RES_OF_2022_05_20_AT_15_00,
                RES_OF_2022_05_20_AT_17_00
        );

        List<TimeSlot> timeSlots = timeSlotsService.getFreeTimeSlots(reservationsOnDay,DATE_OF_2022_05_20,tableNumber);

        assertThat(timeSlots).hasSize(13);
        assertThat(timeSlots).contains(TIME_SLOT_OF_2022_05_20_AT_19_00);
        assertThat(timeSlots).contains(TIME_SLOT_OF_2022_05_20_AT_22_00);

        System.out.println(timeSlots);
    }

    @Test
    @DisplayName("Reservations start at 9:00 till 11 and from 15 till end, so we have one timeslot free between 11 and 15")
    void test_6() {

        int tableNumber = 4;

        List<Reservation> reservationsOnDay = List.of(
                RES_OF_2022_05_20_AT_09_00,
                RES_OF_2022_05_20_AT_11_00,
                RES_OF_2022_05_20_AT_15_00,
                RES_OF_2022_05_20_AT_17_00,
                RES_OF_2022_05_20_AT_19_00,
                RES_OF_2022_05_20_AT_21_00
        );

        List<TimeSlot> timeSlots = timeSlotsService.getFreeTimeSlots(reservationsOnDay,DATE_OF_2022_05_20,tableNumber);

        assertThat(timeSlots).hasSize(1);
        assertThat(timeSlots).contains(TIME_SLOT_OF_2022_05_20_AT_13_00);
    }





}
