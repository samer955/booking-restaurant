# Booking-Restaurant-System
A simple Booking System for a Restaurant.

## Configuration

#### Configurationfile:
`src/main/resources/application.properties`


The reservation hours for the restaurant can be customized in the file above.
They are already set from 09:00 to 22:00.
if you prefer to change them, respect the following format:

```
RESERVATION_START_TIME = HH:MM
RESERVATION_END_TIME = HH:MM
```

### Webapp start:

After you clone the repository run `docker-compose up` in the main directory. It takes a few minutes to build and run the two images.
After that, the MyRestaurant-Application will be avaiable on http://localhost:8080/ . In order to read the emails sent from the MyRestaurant-Application open http://localhost:1080 to have access to the UserInterface of [maildev](https://github.com/maildev/maildev). The last one is a Fake-Smtp-Server that receive every email sended on port 25.


## To know:

### Reservation:

The reservation are given in form of timeslots with intervals of 15 min: 9:00, 9:15, 9:30.. 22:00.
Between every booked reservation must be an interval of 2h. It means that if a table has only on reservation at 9:00, the next free reservation can be booked at 11:00 (if there are no other table free).
A reservation can be canceled from customers using the code received in the confirmation email. This is possible until 3 hours before the reservation of the table start.
The Admin can instead cancels them anytime.

### Table:
The table are static and already saved in the database. There are 5 different table with different capacity (min and max).

Table number 1: capacity from 6 to 8. <br /> 
Table number 2: capacity from 5 to 6. <br />
Table number 3: capacity from 3 to 4. <br />
Table number 4: capacity from 1 to 2. <br />
Table number 5: capacity from 1 to 2. <br />

### Admin:
You can access to the admin view in http://localhost8080/myrestaurant/admin with username: `admin` and password `admin`.








