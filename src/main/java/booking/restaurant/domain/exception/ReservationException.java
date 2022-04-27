package booking.restaurant.domain.exception;

public class ReservationException extends Throwable {

    public static final String ERR_MSG_DATE_OR_TIME_NOT_CORRECT =
            "You can't book a table in the past";

    public static final String ERROR_MSG_CANCEL_BOOKING =
            "You cannot cancel your booking, as there are less than 2 hours to your reservation";

    public static final String ERROR_MSG_WRONG_CODE =
            "The code you entered is incorrect";

    public static final String ERR_NO_TIME_SLOT_AVAILABLE =
            "There are no free time Slots for this Hour";

    public ReservationException(String errorMessage) {
        super(errorMessage);
    }
}
