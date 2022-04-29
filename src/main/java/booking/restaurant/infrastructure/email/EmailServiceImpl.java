package booking.restaurant.infrastructure.email;

import booking.restaurant.domain.model.Customer;
import booking.restaurant.domain.model.Reservation;
import booking.restaurant.application.EmailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    @Async
    public void send(String to, String email) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper =
                new MimeMessageHelper(message, "utf-8");

        helper.setText(email, true);
        helper.setTo(to);
        helper.setSubject("Confirmation Reservation MyRestaurant");
        helper.setFrom("myrestaurant@try.com");
        try{
            mailSender.send(message);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String createConfirmEmail(Customer customer, Reservation res) {

        String email =
                "<p> Dear Customer, </p>" +
                "We confirm your Reservation at MyRestaurant. " +
                "Here a resume of your Booking:" +
                "<p> Reservation for the day: " + res.getDate() + " at: " + res.getTime() +
                " for " + res.getPersons() + " persons at Table " + res.getTableNumber() + "</p>" +
                "Your Personal Information: " +
                "<p> Name: " + customer.name() + "</p>" +
                "<p> Email: " + customer.email() + "</p>" +
                "<p> Telefon " + customer.telefonNumber() + "</p>" +
                "<p> <strong> Keep in mind that you can cancel for free your reservation until 3 hours before your reservation begin " +
                "using the following code: " + res.getCode() + "</strong></p>";

        return email;
    }

    @Override
    public String createCancelEmail(Customer customer, Reservation res) {

        String email =
                "<p> Dear Customer, </p>" +
                "<p> We have received the cancellation of your booking" +
                "for the day: " + res.getDate() + " at: " + res.getTime() + "</p>" +
                "<p> Your personal data: </p>" +
                "<p> Name: " + customer.name() + "</p>" +
                "<p> Email: " + customer.email() + "</p>" +
                "<p> Telefon " + customer.telefonNumber() + "</p>";

        return email;
    }

}