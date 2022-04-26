package booking.restaurant.domain.exception;

public class ReservationException extends Throwable {

    public static final String ERR_MSG_DATE_OR_TIME_NOT_CORRECT =
            "You can't book a table in the past";

    public static final String ERROR_MSG_CANCEL_BOOKING =
            "You cannot cancel your booking, as there are less than 2 hours to your reservation";

    public ReservationException(String errorMessage) {
        super(errorMessage);
    }
}
