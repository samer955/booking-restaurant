package booking.restaurant.infrastructure.web.controllers;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public record FormularForm(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                           @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime time,
                           int persons) {
}
