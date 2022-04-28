package booking.restaurant.infrastructure.web.controllers;

import booking.restaurant.domain.model.Reservation;
import booking.restaurant.service.BookingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import static booking.restaurant.domain.exception.ReservationException.NO_RESERVATION;
import static booking.restaurant.domain.exception.ReservationException.SUCCESS_CANCEL_ADMIN;

@Controller
public class AdminController {

    private final BookingService bookingService;

    public AdminController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/myrestaurant/admin")
    public String adminView(Model model, Principal user, @RequestParam(
            name = "date", defaultValue = "#{T(java.time.LocalDate).now()}",
            required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        List<Reservation> reservations = bookingService.getAllReservationsByDate(date);
        if(reservations.size() == 0) {
            model.addAttribute("nores", NO_RESERVATION);
        }
        model.addAttribute("date", date);
        model.addAttribute("reservations", reservations);
        return "admin";
    }


    @Secured("ROLE_ADMIN")
    @PostMapping("/myrestaurant/admin/delete/{id}")
    public String deleteReservation(@PathVariable("id") Long id, RedirectAttributes attr) {
        Reservation reservation = bookingService.findById(id);
        bookingService.cancelReservationAdmin(id);
        attr.addFlashAttribute("success", SUCCESS_CANCEL_ADMIN);
        return "redirect:/myrestaurant/admin" + "?date=" + reservation.getDate();
    }
}
