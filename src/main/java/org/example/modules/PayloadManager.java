package org.example.modules;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import org.example.pojos.request.Auth;
import org.example.pojos.request.Booking;
import org.example.pojos.request.Bookingdates;
import org.example.pojos.response.BookingResponse;
import org.example.pojos.response.TokenResponse;

public class PayloadManager {
    Gson gson;
    Faker faker;

    //Payload for Create Booking
    public String createPayloadBookingAsString()
    {
        Booking booking = new Booking();
        booking.setFirstname("Prateek");
        booking.setLastname("Sharma");
        booking.setTotalprice(666);
        booking.setDepositpaid(true);

        Bookingdates bookingdates = new Bookingdates();
        bookingdates.setCheckin("2025-08-03");
        bookingdates.setCheckout("2025-08-04");
        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds("Breakfast");

        System.out.println(booking);
        //Convert the Java Object into the JSON String to use as Payload
        //Serialization
        //This payload is completely in Java Object and this need to be converted in JSON
         gson =new Gson();
        String jsonStringBooking =gson.toJson(booking);
        return jsonStringBooking;
    }

    public String createPayloadBookingAsStringWrongBody()
    {
        Booking booking = new Booking();
        booking.setFirstname("普拉泰克");
        booking.setLastname("夏爾馬");
        booking.setTotalprice(666);
        booking.setDepositpaid(false);

        Bookingdates bookingdates = new Bookingdates();
        bookingdates.setCheckin("5025-08-03");
        bookingdates.setCheckout("5025-08-04");
        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds("夏爾馬");

        System.out.println(booking);
        //Convert the Java Object into the JSON String to use as Payload
        //Serialization
        //This payload is completely in Java Object and this need to be converted in JSON
        gson =new Gson();
        String jsonStringBooking =gson.toJson(booking);
        return jsonStringBooking;
    }
    //Convert the JSON String to Java Object so that we can verify response
    //De-Serialization
    public BookingResponse bookingResponseJava(String responseString)
    {
        gson =new Gson();
        BookingResponse bookingResponse =gson.fromJson(responseString, BookingResponse.class);
        return bookingResponse;
    }
    //Auth-Payload
    public String setAuthPayload()
    {
        Auth auth = new Auth();
        auth.setUsername("admin");
        auth.setPassword("password123");
        //Serialization of JAVA to JSON
        gson =new Gson();
        String jsonPayloadString =gson.toJson(auth);
        System.out.println("Payload set to the ->"+ jsonPayloadString);
        return jsonPayloadString;
    }
    //De-Serialization of JSON to Java
    public String getTokenFromJSON(String tokenResponse)
    {
        gson = new Gson();
        TokenResponse tokenResponse1 = gson.fromJson(tokenResponse,TokenResponse.class);
        return tokenResponse1.getToken();
    }
    //Payload generation randomly at run time
    public String createPayloadBookingFakerJS()
    {
        faker =new Faker();
        Booking booking = new Booking();
        booking.setFirstname(faker.name().firstName());
        booking.setLastname(faker.name().lastName());
        booking.setTotalprice(faker.random().nextInt(1,1000));
        booking.setDepositpaid(faker.random().nextBoolean());

        Bookingdates bookingdates = new Bookingdates();
        bookingdates.setCheckin("2025-08-03");
        bookingdates.setCheckout("2025-08-04");
        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds("Breakfast");

        System.out.println(booking);
        //Convert the Java Object into the JSON String to use as Payload
        //Serialization
        //This payload is completely in Java Object and this need to be converted in JSON
        gson =new Gson();
        String jsonStringBooking =gson.toJson(booking);
        return jsonStringBooking;
    }
    //Payload for getting Booking Class from GET Request
     //Get request sends booking as a response not booking response
    public Booking getResponseFromJSON(String getResponse)
    {
        gson =new Gson();
        Booking booking =gson.fromJson(getResponse, Booking.class);
        return booking;

    }
    //Payload for full updation request of bookingid
    public String fullUpdatePayloadAsString()
    {
        Booking booking =new Booking();
        booking.setFirstname("Prateek");
        booking.setLastname("Sharma");
        booking.setTotalprice(222);
        booking.setDepositpaid(true);

        Bookingdates bookingdates =new Bookingdates();
        bookingdates.setCheckin("2025-08-25");
        bookingdates.setCheckout("2025-08-29");
        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds("Breakfast");
        return gson.toJson(booking);
    }

    //Payload for partial updating the booking id
    public String partialUpdatePayloadAsString()
    {
        Booking booking =new Booking();
        booking.setFirstname("Prateek");
        booking.setLastname("Sharma");
        return gson.toJson(booking);
    }


}
