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

##### method 1:

Start the app with gradle in the main directory:
```console
User@Desktop:~$ ./gradlew bootRun
.
.
.
2022-04-29 17:28:58.232  INFO 86616 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''

<==========---> 80% EXECUTING [26s]
> :bootRun
```

The MyRestaurant Application will be avaiable on http://localhost:8080/ and it uses a in-memory h2 database. If you want to check the stored data, go to http://localhost:8080/h2-console 

The Application uses the Interface Java Mail Sender to send confirmation email to the customers. To have access to the email I use one Fake-SMTP-Server to test the email.
One possibility is to use [maildev](https://github.com/maildev/maildev).
You can install it locally like me or just give the following command using Docker to pull and run the image :
`docker run -p 1080:1080 -p 1025:1025 maildev/maildev`
 .It starts a WebApp on Port http://localhost:1080 where you can read the emails and listening on port 1025.


##### method 2:
Building and running an image with Docker using the following commands:
`docker build -t myrestaurant .` (in the main directory)
and after running the app with docker `run -dp 8080:8080 myrestaurant`

The email 

##### method 3:
Start the programm with your IDE (IntelliJ/Eclipse) and start [maildev](https://github.com/maildev/maildev) separately.


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








