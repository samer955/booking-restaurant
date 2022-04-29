package booking.restaurant.infrastructure.web.controllers;

import booking.restaurant.domain.model.Customer;

public record CustomerInfoForm(String name, String email, String telefonNumber, String note) {

    public Customer toCustomer() {
        return new Customer(name,email,telefonNumber);
    }
}
