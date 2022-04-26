package booking.restaurant.domain;

import java.time.LocalTime;

public record ReservationTime(LocalTime start, LocalTime end) {
}
