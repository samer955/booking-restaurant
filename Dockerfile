FROM gradle:7.3-jdk17 AS BUILD
WORKDIR /booking-restaurant
COPY . /booking-restaurant
RUN gradle bootJar

FROM eclipse-temurin:17-alpine
WORKDIR /restaurant
COPY --from=BUILD /booking-restaurant/build/libs/*.jar \
                  /restaurant/restaurant.jar
EXPOSE 8080
CMD java -jar restaurant.jar
