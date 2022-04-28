package booking.restaurant.infrastructure.web;

import booking.restaurant.domain.model.Customer;

public record CustomerInfoForm(String name, String email, String telefonNumber) {

    public Customer toCustomer() {
        return new Customer(name,email,telefonNumber);
    }
}