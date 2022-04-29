package booking.restaurant.infrastructure.web.controllers;

import booking.restaurant.domain.exception.ReservationException;
import booking.restaurant.domain.model.TimeSlot;
import booking.restaurant.application.BookingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static booking.restaurant.domain.exception.ReservationException.*;
import static booking.restaurant.infrastructure.web.controllers.Routes.*;

@Controller
public class CustomerController {

    private final BookingService bookingService;

    public CustomerController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping(INDEX)
    public String redirect() {
        return "redirect:" + HOME;
    }

    @GetMapping(HOME)
    public String getIndexView(Model model) {
        model.addAttribute("remember", TO_REMEMBER);
        return "formular";
    }

    @PostMapping(HOME)
    public String postFormular(Model model, FormularForm form, HttpSession session, RedirectAttributes attr){
        //save the attributes to use after at the end of the reservaton
        session.setAttribute("date", form.date());
        session.setAttribute("persons", form.persons());

        List<TimeSlot> freeSlots;
        try {
            freeSlots = bookingService.findFreeTable(form.persons(), form.date(), form.time());
        } catch (ReservationException e) {
            attr.addFlashAttribute("error", e.getMessage());
            return "redirect:" + HOME;
        }
        if(freeSlots.size() == 0) {
            model.addAttribute("error", ERR_NO_TIME_SLOT_AVAILABLE);
        }
        model.addAttribute("timeslots", freeSlots);
        return "timeslots";
    }

    @GetMapping(BOOK_TABLE + "/{table}")
    public String getReservationView(@PathVariable("table") int tableNumber, @ModelAttribute("slotTime") LocalTime time, Model model, HttpSession session) {
        session.setAttribute("time",time);
        model.addAttribute("table", tableNumber);
        return "reservation";
    }

    @PostMapping(CLOSE_BOOKING + "/{table}")
    public String postReservation(@PathVariable("table") int tableNumber, CustomerInfoForm form, HttpSession session, RedirectAttributes attr) {
        //get session attributes
        LocalDate date = (LocalDate) session.getAttribute("date");
        int persons = (int) session.getAttribute("persons");
        LocalTime time = (LocalTime) session.getAttribute("time");

        TimeSlot timeSlot = new TimeSlot(date,time,tableNumber);
        bookingService.closeBooking(form.toCustomer(),form.note(),persons,timeSlot);
        session.removeAttribute("date");
        session.removeAttribute("time");
        session.removeAttribute("persons");

        attr.addFlashAttribute("success", SUCCESS_BOOKING);
        return "redirect:" + HOME;
    }

    @PostMapping(CANCEL_RESERVATION)
    public String cancelReservation(@RequestParam("code") String code, RedirectAttributes attr) {
        try {
            bookingService.cancelReservation(code);
        } catch (ReservationException e) {
            attr.addFlashAttribute("error", e.getMessage());
            return "redirect:" + HOME;
        }
        attr.addFlashAttribute("success", SUCCESS_CANCEL_BOOKING);
        return "redirect:" + HOME;
    }

}
