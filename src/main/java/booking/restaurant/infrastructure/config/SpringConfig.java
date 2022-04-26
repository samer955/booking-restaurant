package booking.restaurant.infrastructure.config;

import booking.restaurant.domain.ReservationTime;
import booking.restaurant.domain.service.TimeSlotsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.LocalTime;

@Configuration
public class SpringConfig {

    @Bean
    public ReservationTime createReservationTime(@Value("#{T(java.time.LocalTime).parse('${RESERVATION_START_TIME}')}") LocalTime startTime,
                                                 @Value("#{T(java.time.LocalTime).parse('${RESERVATION_END_TIME}')}") LocalTime endTime) {
        return new ReservationTime(startTime,endTime);
    }

    @Bean
    public TimeSlotsService createService(ReservationTime reservationTime) {
        return new TimeSlotsService(reservationTime);
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

}
