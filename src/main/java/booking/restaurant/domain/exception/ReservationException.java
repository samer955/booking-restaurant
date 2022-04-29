package booking.restaurant.domain.exception;

public class ReservationException extends Throwable {

    public static final String ERR_MSG_DATE_OR_TIME_NOT_CORRECT =
            "You can't book a table in the past";

    public static final String ERROR_MSG_CANCEL_BOOKING =
            "You cannot cancel your booking, as there are less than 2 hours to your reservation";

    public static final String ERROR_MSG_WRONG_CODE =
            "The code you entered is incorrect";

    public static final String ERR_NO_TIME_SLOT_AVAILABLE =
            "There are no free timeslots for this Hour or the number of persons doesn't fit";

    public static final String SUCCESS_CANCEL_BOOKING =
            "Your booking was successfully canceled, a confirmation was sent to your Email account.";

    public static final String SUCCESS_BOOKING =
            "Thank you for your booking! A confirmation Email was sent to your Email account.";

    public static final String TO_REMEMBER =
            "Dear Customer, keep in mind that the maximal number of persons for a reservation is 8. \n" +
                    "If you book a table remember that your reservation is valid for 2h.";

    public static final String SUCCESS_CANCEL_ADMIN =
            "Reservation successfully deleted";

    public static final String NO_RESERVATION =
            "There is no reservation for this day";

    public ReservationException(String errorMessage) {
        super(errorMessage);
    }
}
