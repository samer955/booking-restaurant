package booking.restaurant.infrastructure.web.controllers;

import booking.restaurant.domain.exception.ReservationException;
import booking.restaurant.domain.model.TimeSlot;
import booking.restaurant.infrastructure.web.FormularForm;
import booking.restaurant.service.BookingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
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
}
