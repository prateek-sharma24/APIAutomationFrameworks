package org.example.modules;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import org.example.pojos.request.Auth;
import org.example.pojos.request.Booking;
import org.example.pojos.request.Bookingdates;
import org.example.pojos.response.BookingResponse;
import org.example.pojos.response.TokenResponse;

public class PayloadManager {
    private static final Gson gson = new Gson();
    private static final Faker faker = new Faker();
    
    // Constants for default values
    private static final String DEFAULT_CHECKIN = "2025-08-03";
    private static final String DEFAULT_CHECKOUT = "2025-08-04";
    private static final String DEFAULT_ADDITIONAL_NEEDS = "Breakfast";
    private static final String DEFAULT_USERNAME = "admin";
    private static final String DEFAULT_PASSWORD = "password123";

    public String createPayloadBookingAsString() {
        return createBookingPayload("Prateek", "Sharma", 666, true, DEFAULT_CHECKIN, DEFAULT_CHECKOUT, DEFAULT_ADDITIONAL_NEEDS);
    }

    public String createPayloadBookingAsStringWrongBody() {
        return createBookingPayload("普拉泰克", "夏爾馬", 666, false, "5025-08-03", "5025-08-04", "夏爾馬");
    }
    public BookingResponse bookingResponseJava(String responseString) {
        return gson.fromJson(responseString, BookingResponse.class);
    }
    public String setAuthPayload() {
        return createAuthPayload(DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }
    
    public String createAuthPayload(String username, String password) {
        Auth auth = new Auth();
        auth.setUsername(username);
        auth.setPassword(password);
        return gson.toJson(auth);
    }
    public String getTokenFromJSON(String tokenResponse) {
        TokenResponse response = gson.fromJson(tokenResponse, TokenResponse.class);
        return response.getToken();
    }
    public String createPayloadBookingFakerJS() {
        return createBookingPayload(
            faker.name().firstName(),
            faker.name().lastName(),
            faker.random().nextInt(1, 1000),
            faker.random().nextBoolean(),
            DEFAULT_CHECKIN,
            DEFAULT_CHECKOUT,
            DEFAULT_ADDITIONAL_NEEDS
        );
    }
    public Booking getResponseFromJSON(String getResponse) {
        return gson.fromJson(getResponse, Booking.class);
    }
    public String fullUpdatePayloadAsString() {
        return createBookingPayload("Prateek", "Sharma", 222, true, "2025-08-25", "2025-08-29", DEFAULT_ADDITIONAL_NEEDS);
    }

    public String partialUpdatePayloadAsString() {
        Booking booking = new Booking();
        booking.setFirstname("Prateek");
        booking.setLastname("Sharma");
        return gson.toJson(booking);
    }
    
    // Helper method to create booking payload with parameters
    private String createBookingPayload(String firstName, String lastName, int totalPrice, 
                                       boolean depositPaid, String checkin, String checkout, String additionalNeeds) {
        Booking booking = new Booking();
        booking.setFirstname(firstName);
        booking.setLastname(lastName);
        booking.setTotalprice(totalPrice);
        booking.setDepositpaid(depositPaid);
        
        Bookingdates bookingdates = new Bookingdates();
        bookingdates.setCheckin(checkin);
        bookingdates.setCheckout(checkout);
        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds(additionalNeeds);
        
        return gson.toJson(booking);
    }
}
