package booking.restaurant.application;

import booking.restaurant.domain.ReservationTime;
import booking.restaurant.domain.exception.ReservationException;
import booking.restaurant.domain.model.Reservation;
import booking.restaurant.domain.model.TimeSlot;
import booking.restaurant.domain.service.TimeSlotsService;
import booking.restaurant.service.repository.ReservationRepository;
import booking.restaurant.service.repository.TableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;


import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static booking.restaurant.TestHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ActiveProfiles("test")
@DataJdbcTest(properties = "spring.flyway.enabled=false")
public class BookingServiceTest {

    @MockBean
    ReservationRepository reservationRepository;

    @MockBean
    TableRepository tableRepository;

    @MockBean
    EmailService emailService;

    TimeSlotsService timeSlotsService;

    final Clock clock = Clock.fixed(Instant.parse("2022-05-19T10:00:00.00Z"),ZoneId.systemDefault());

    BookingService bookingService;

    @BeforeEach
    void setup() {
        timeSlotsService = new TimeSlotsService(new ReservationTime(START_RESERVATION,END_RESERVATION));
        bookingService = new BookingService(reservationRepository, tableRepository, timeSlotsService, emailService, clock);
    }

    @Test
    void test_1() {
        assertThat(bookingService).isNotNull();
    }

    @Test
    @DisplayName("Table with no Reservations, all timeslots from 9:00 till 9:45 are returned")
    void test_2() throws ReservationException {

        when(tableRepository.findAllByMinOrMaxCapacity(3,3)).thenReturn(List.of(TABLE_3_MIN_3_MAX_4));
        when(reservationRepository.findAllByDateAndTableNumber(DATE_OF_2022_05_20,3)).thenReturn(new ArrayList<>());

        List<TimeSlot> freeSlots = bookingService.findFreeTable(3, DATE_OF_2022_05_20, TIME_OF_09_00);

        assertThat(freeSlots).hasSize(4);
        assertThat(freeSlots).contains(TIME_SLOT_OF_2022_05_20_AT_09_00);
        assertThat(freeSlots).contains(TIME_SLOT_OF_2022_05_20_AT_09_45);
    }

    @Test
    @DisplayName("Table with Reservation, no free timeslots from 9:00 till 11:00")
    void test_3() throws ReservationException {

        when(tableRepository.findAllByMinOrMaxCapacity(3,3)).thenReturn(List.of(TABLE_3_MIN_3_MAX_4));
        when(reservationRepository.findAllByDateAndTableNumber(DATE_OF_2022_05_20,3)).thenReturn(List.of(RES_OF_2022_05_20_AT_09_00));

        List<TimeSlot> freeSlots = bookingService.findFreeTable(3, DATE_OF_2022_05_20, TIME_OF_09_00);

        assertThat(freeSlots).hasSize(4);
        assertThat(freeSlots).doesNotContain(TIME_SLOT_OF_2022_05_20_AT_09_00);
        assertThat(freeSlots).doesNotContain(TIME_SLOT_OF_2022_05_20_AT_09_45);
        assertThat(freeSlots).contains(TIME_SLOT_OF_2022_05_20_AT_11_00);
    }

    @Test
    @DisplayName("One Table with Reservation, the other without give the expected timeslots")
    void test_4() throws ReservationException {

        when(tableRepository.findAllByMinOrMaxCapacity(2,2)).thenReturn(List.of(TABLE_4_MIN_1_MAX_2,TABLE_5_MIN_1_MAX_2));
        when(reservationRepository.findAllByDateAndTableNumber(DATE_OF_2022_05_20,4)).thenReturn(List.of(RES_OF_2022_05_20_AT_09_00));

        List<TimeSlot> freeSlots = bookingService.findFreeTable(2, DATE_OF_2022_05_20, TIME_OF_09_00);

        assertThat(freeSlots).hasSize(4);
        assertThat(freeSlots).contains(TIME_SLOT_OF_2022_05_20_AT_09_00);
    }

    @Test
    @DisplayName("Table full of Reservation, no free timeslots the whole day")
    void test_5() throws ReservationException {

        List<Reservation> reservationsOnDay = List.of(
                RES_OF_2022_05_20_AT_09_00,
                RES_OF_2022_05_20_AT_11_00,
                RES_OF_2022_05_20_AT_13_00,
                RES_OF_2022_05_20_AT_15_00,
                RES_OF_2022_05_20_AT_17_00,
                RES_OF_2022_05_20_AT_19_00,
                RES_OF_2022_05_20_AT_21_00
        );
        when(tableRepository.findAllByMinOrMaxCapacity(2,2)).thenReturn(List.of(TABLE_4_MIN_1_MAX_2));
        when(reservationRepository.findAllByDateAndTableNumber(DATE_OF_2022_05_20,4)).thenReturn(reservationsOnDay);

        List<TimeSlot> freeSlots = bookingService.findFreeTable(2, DATE_OF_2022_05_20, TIME_OF_09_00);

        assertThat(freeSlots).hasSize(0);
    }



    @Test
    @DisplayName("Customer get error message when he try to book a table in the past")
    void test_6() {

        Throwable exceptionThatWasThrown = assertThrows(ReservationException.class, () ->
                bookingService.findFreeTable(2,DATE_OF_2022_05_18,TIME_OF_09_00));

        assertThat(exceptionThatWasThrown.getMessage())
                .isEqualTo(ReservationException.ERR_MSG_DATE_OR_TIME_NOT_CORRECT);
    }

    @Test
    @DisplayName("Customer get error message when he try to book a table the same day but after the time")
    void test_7() {

        Throwable exceptionThatWasThrown = assertThrows(ReservationException.class, () ->
                bookingService.findFreeTable(2,DATE_OF_2022_05_19,TIME_OF_09_00));

        assertThat(exceptionThatWasThrown.getMessage())
                .isEqualTo(ReservationException.ERR_MSG_DATE_OR_TIME_NOT_CORRECT);
    }






}
