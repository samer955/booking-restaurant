package booking.restaurant.infrastructure.web.controllers;

import booking.restaurant.domain.exception.ReservationException;
import booking.restaurant.domain.model.TimeSlot;
import booking.restaurant.infrastructure.web.CustomerInfoForm;
import booking.restaurant.infrastructure.web.FormularForm;
import booking.restaurant.service.BookingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static booking.restaurant.domain.exception.ReservationException.ERR_NO_TIME_SLOT_AVAILABLE;

@Controller
public class CustomerController {

    private final BookingService bookingService;

    public CustomerController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/")
    public String getIndexView() {
        return "formular";
    }

    @PostMapping("/formular")
    public String postFormular(Model model, FormularForm form, HttpSession session, RedirectAttributes attr){

        session.setAttribute("date", form.date());
        session.setAttribute("time", form.time());
        session.setAttribute("persons", form.persons());

        List<TimeSlot> freeSlots;

        try {
            freeSlots = bookingService.findFreeTable(form.persons(), form.date(), form.time());
        } catch (ReservationException e) {
            attr.addFlashAttribute("error", e.getMessage());
            return "redirect:/";
        }

        if(freeSlots.size() == 0) {
            model.addAttribute("error", ERR_NO_TIME_SLOT_AVAILABLE);

        }
        model.addAttribute("timeslots", freeSlots);
        return "timeslots";
    }

    @GetMapping("/book/{table}")
    public String getReservationView(@PathVariable("table") int tableNumber, Model model) {
        model.addAttribute("table", tableNumber);
        return "reservation";
    }

    @PostMapping("/close/booking/{table}")
    public String postReservation(@PathVariable("table") int tableNumber, Model model, CustomerInfoForm form, HttpSession session) {

        LocalDate date = (LocalDate) session.getAttribute("date");
        LocalTime time = (LocalTime) session.getAttribute("time");
        int persons = (int) session.getAttribute("persons");

        TimeSlot timeSlot = new TimeSlot(date,time,tableNumber);
        bookingService.closeBooking(form.toCustomer(),null,persons,timeSlot);
        model.addAttribute("success", " Thank you for your booking! A confirmation Email was sended to your Email account.");
        return "message";
    }
}
