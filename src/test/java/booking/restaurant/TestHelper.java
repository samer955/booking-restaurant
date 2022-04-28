package booking.restaurant;

import booking.restaurant.domain.model.Reservation;
import booking.restaurant.domain.model.Table;
import booking.restaurant.domain.model.TimeSlot;

import java.time.LocalDate;
import java.time.LocalTime;

public class TestHelper {

    public static final LocalTime START_RESERVATION = LocalTime.of(9, 0);
    public static final LocalTime END_RESERVATION   = LocalTime.of(22, 0);

    public static final LocalDate DATE_OF_2022_05_20 = LocalDate.of(2022, 5,20);
    public static final LocalDate DATE_OF_2022_05_19 = LocalDate.of(2022, 5,19);
    public static final LocalDate DATE_OF_2022_05_18 = LocalDate.of(2022, 5,18);

    public static final LocalTime TIME_OF_09_00 = LocalTime.of(9,0);
    public static final LocalTime TIME_OF_09_45 = LocalTime.of(9,45);
    public static final LocalTime TIME_OF_10_00 = LocalTime.of(10,0);
    public static final LocalTime TIME_OF_11_00 = LocalTime.of(11,0);
    public static final LocalTime TIME_OF_12_00 = LocalTime.of(12,0);
    public static final LocalTime TIME_OF_13_00 = LocalTime.of(13,0);
    public static final LocalTime TIME_OF_14_00 = LocalTime.of(14,0);
    public static final LocalTime TIME_OF_15_00 = LocalTime.of(15,0);
    public static final LocalTime TIME_OF_16_00 = LocalTime.of(16,0);
    public static final LocalTime TIME_OF_17_00 = LocalTime.of(17,0);
    public static final LocalTime TIME_OF_18_00 = LocalTime.of(18,0);
    public static final LocalTime TIME_OF_19_00 = LocalTime.of(19,0);
    public static final LocalTime TIME_OF_20_00 = LocalTime.of(20,0);
    public static final LocalTime TIME_OF_21_00 = LocalTime.of(21,0);
    public static final LocalTime TIME_OF_22_00 = LocalTime.of(22,0);


    public static final Reservation RES_OF_2022_05_20_AT_09_00 = new Reservation(null,DATE_OF_2022_05_20, TIME_OF_09_00,null,0,4);
    public static final Reservation RES_OF_2022_05_20_AT_11_00 = new Reservation(null,DATE_OF_2022_05_20, TIME_OF_11_00,null,0,4);
    public static final Reservation RES_OF_2022_05_20_AT_13_00 = new Reservation(null,DATE_OF_2022_05_20, TIME_OF_13_00,null,0,4);
    public static final Reservation RES_OF_2022_05_20_AT_15_00 = new Reservation(null,DATE_OF_2022_05_20, TIME_OF_15_00,null,0,4);
    public static final Reservation RES_OF_2022_05_20_AT_17_00 = new Reservation(null,DATE_OF_2022_05_20, TIME_OF_17_00,null,0,4);
    public static final Reservation RES_OF_2022_05_20_AT_19_00 = new Reservation(null,DATE_OF_2022_05_20, TIME_OF_19_00,null,0,4);
    public static final Reservation RES_OF_2022_05_20_AT_21_00 = new Reservation(null,DATE_OF_2022_05_20, TIME_OF_21_00,null,0,4);
    public static final Reservation RES_OF_2022_05_20_AT_22_00 = new Reservation(null,DATE_OF_2022_05_20, TIME_OF_22_00,null,0,4);



    public static final TimeSlot TIME_SLOT_OF_2022_05_20_AT_09_00 = new TimeSlot(DATE_OF_2022_05_20, TIME_OF_09_00,4);
    public static final TimeSlot TIME_SLOT_OF_2022_05_20_AT_09_45 = new TimeSlot(DATE_OF_2022_05_20, TIME_OF_09_45,4);
    public static final TimeSlot TIME_SLOT_OF_2022_05_20_AT_11_00 = new TimeSlot(DATE_OF_2022_05_20, TIME_OF_11_00,4);
    public static final TimeSlot TIME_SLOT_OF_2022_05_20_AT_13_00 = new TimeSlot(DATE_OF_2022_05_20, TIME_OF_13_00,4);
    public static final TimeSlot TIME_SLOT_OF_2022_05_20_AT_19_00 = new TimeSlot(DATE_OF_2022_05_20, TIME_OF_19_00,4);
    public static final TimeSlot TIME_SLOT_OF_2022_05_20_AT_22_00 = new TimeSlot(DATE_OF_2022_05_20, TIME_OF_22_00,4);

    public static final Table TABLE_1_MIN_5_MAX_6 = new Table(1L,1,5,6);
    public static final Table TABLE_2_MIN_4_MAX_5 = new Table(2L,2,4,5);
    public static final Table TABLE_3_MIN_3_MAX_4 = new Table(3L,3,3,4);
    public static final Table TABLE_4_MIN_1_MAX_2 = new Table(4L,4,1,2);
    public static final Table TABLE_5_MIN_1_MAX_2 = new Table(5L,5,1,2);

}
