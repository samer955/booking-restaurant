package booking.restaurant.infrastructure;

import booking.restaurant.application.BookingService;
import booking.restaurant.domain.exception.ReservationException;
import booking.restaurant.domain.model.Customer;
import booking.restaurant.domain.model.TimeSlot;
import booking.restaurant.infrastructure.web.controllers.AdminController;
import booking.restaurant.infrastructure.web.controllers.CustomerController;
import booking.restaurant.infrastructure.web.securty.MethodSecurityConfiguration;
import booking.restaurant.infrastructure.web.securty.WebSecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static booking.restaurant.TestHelper.*;
import static booking.restaurant.domain.exception.ReservationException.*;
import static booking.restaurant.infrastructure.web.controllers.Routes.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import({MethodSecurityConfiguration.class, WebSecurityConfig.class})
@WebMvcTest({CustomerController.class, AdminController.class})
public class ControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    BookingService bookingService;

    @Test
    void test_0() {
        assertThat(bookingService).isNotNull();
    }


    @DisplayName("/ get redirect to /myrestaurant")
    @Test
    void test_1() throws Exception {
        mvc.perform(get(INDEX)).andExpect(status().is3xxRedirection());
    }

    @DisplayName("/ get redirect to /myrestaurant")
    @Test
    void test_2() throws Exception {
        mvc.perform(get(HOME))
                .andExpect(status().isOk())
                .andExpect(view().name("formular"));
    }

    @DisplayName("User chooses a day and time and gets the next free timeslots avaiable")
    @Test
    void test_3() throws Exception, ReservationException {

        when(bookingService.findFreeTable(4, DATE_OF_2022_05_20, TIME_OF_09_00)).thenReturn(
                List.of(TIME_SLOT_OF_2022_05_20_AT_09_00,
                        TIME_SLOT_OF_2022_05_20_AT_13_00)
        );

        var html = mvc.perform(post(HOME)
                        .param("date", "2022-05-20")
                        .param("persons", "4")
                        .param("time", "09:00"))
                .andExpect(status().isOk())
                .andExpect(view().name("timeslots"))
                .andReturn();

        assertThat(html.getResponse().getContentAsString()).contains("2022-05-20","09:00","13:00");
    }

    @DisplayName("User choose a day and time but no timeslots are avaiable")
    @Test
    void test_4() throws Exception, ReservationException {

        when(bookingService.findFreeTable(4, DATE_OF_2022_05_20, TIME_OF_09_00)).thenReturn(
                new ArrayList<>()
        );

        mvc.perform(post(HOME)
                        .param("date", "2022-05-20")
                        .param("persons", "4")
                        .param("time", "09:00"))
                .andExpect(status().isOk())
                .andExpect(view().name("timeslots"))
                .andExpect(model().attribute("error", ERR_NO_TIME_SLOT_AVAILABLE));
    }

    @DisplayName("User choose a wrong day/time and get redirect to the same page with error message")
    @Test
    void test_5() throws Exception, ReservationException {

        doThrow(new ReservationException(ERR_MSG_DATE_OR_TIME_NOT_CORRECT))
                .when(bookingService)
                .findFreeTable(any(int.class),any(LocalDate.class),any(LocalTime.class));

        mvc.perform(post(HOME)
                        .param("date", "2022-05-20")
                        .param("persons", "4")
                        .param("time", "09:00"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:" + HOME))
                .andExpect(flash().attribute("error", ERR_MSG_DATE_OR_TIME_NOT_CORRECT));
    }


    @DisplayName("User close a booking and gets redirected to Home with success message")
    @Test
    void test_6() throws Exception {

        HashMap<String, Object> sessionattr = new HashMap<>();
        sessionattr.put("date", DATE_OF_2022_05_18);
        sessionattr.put("persons", 4);
        sessionattr.put("time", TIME_OF_09_00);

        Customer customer = new Customer("Steve","steve@gmail.com","123456");
        String note = "Hello test i hope you will work";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", "Steve");
        params.add("email", "steve@gmail.com");
        params.add("telefonNumber", "123456");
        params.add("note", "Hello test i hope you will work");

        mvc.perform(post(CLOSE_BOOKING + "/" + "{table}", 4)
                        .sessionAttrs(sessionattr)
                        .params(params))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:" + HOME))
                .andExpect(flash().attribute("success", SUCCESS_BOOKING));

        verify(bookingService).closeBooking(customer,note,4,new TimeSlot(DATE_OF_2022_05_18,TIME_OF_09_00,4));
    }


    @DisplayName("User cannot cancel Reservation and gets error message")
    @Test
    void test_7() throws Exception, ReservationException {

        doNothing().when(bookingService).cancelReservation(any(String.class));

        mvc.perform(post(CANCEL_RESERVATION)
                        .param("code", "abcd"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:" + HOME))
                .andExpect(flash().attribute("success", SUCCESS_CANCEL_BOOKING));
    }

    @DisplayName("User can cancel reservaton and get success message")
    @Test
    void test_8() throws Exception, ReservationException {

        doThrow(new ReservationException(ERROR_MSG_CANCEL_BOOKING))
                .when(bookingService)
                .cancelReservation(any(String.class));

        mvc.perform(post(CANCEL_RESERVATION)
                        .param("code", "abcd"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:" + HOME))
                .andExpect(flash().attribute("error", ERROR_MSG_CANCEL_BOOKING));
    }

    @DisplayName("Customer cannot access admin page, gets redirect to login")
    @Test
    void test_9() throws Exception {
        mvc.perform(get(ADMIN))
                .andExpect(status().is3xxRedirection());
    }

    @DisplayName("Admin can access admin page")
    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void test_10() throws Exception {
        mvc.perform(get(ADMIN))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"));
    }

    @DisplayName("Admin can access cancel Reservation")
    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void test_11() throws Exception {

        when(bookingService.findById(4L)).thenReturn(RES_OF_2022_05_20_AT_09_00);

        mvc.perform(post(ADMIN_CANCEL_RESERVATION + "/" + "{id}", 4L))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("success", SUCCESS_CANCEL_ADMIN));

        verify(bookingService).cancelReservationAdmin(4L);
    }



















}
