package booking.restaurant.infrastructure.config;

import booking.restaurant.domain.ReservationTime;
import booking.restaurant.domain.service.TimeSlotsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.time.Clock;
import java.time.LocalTime;
import java.util.Properties;

@Configuration
public class SpringConfig {

    //set the port and protocol for the mailsender
    @Bean
    public JavaMailSender getJavaMailSender()
    {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("maildev");
        mailSender.setPort(1025);
        mailSender.setUsername("sa");
        mailSender.setPassword("sa");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "false");

        return mailSender;
    }

    //injection from the property
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
