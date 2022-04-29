package booking.restaurant.application;


import booking.restaurant.domain.model.Customer;
import booking.restaurant.domain.model.Reservation;

import javax.mail.MessagingException;

public interface EmailService {

    void send(String to, String email) throws MessagingException;

    String createConfirmEmail(Customer customer, Reservation res);

    String createCancelEmail(Customer customer, Reservation res);
}
