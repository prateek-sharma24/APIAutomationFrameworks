package org.example.tests.crud;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.example.base.BaseTest;
import org.example.endpoints.APIConstants;
import org.example.modules.PayloadManager;
import org.example.pojos.response.BookingResponse;
import org.testng.annotations.Test;

public class TestCreateBooking extends BaseTest {
    @Test(groups = "reg",priority = 1)
    @Owner("Prateek Sharma")
    @Description("TC#1 -Verify that the Booking can be Created")
    public void testCreateBookingPOST_Positive()
    {
        //Setup and Making a Request
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response = RestAssured.given(requestSpecification).when().body(payloadManager.createPayloadBookingAsString()).log().all().post();
        System.out.println(response.asString());

        //Extraction
        BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());

        //Verification Part
        assertActions.verifyStringKeyNotNull(bookingResponse.getBookingid());
        assertActions.verifyStringKey(bookingResponse.getBooking().getFirstname(),"Prateek");


    }
    @Test(groups = "reg",priority = 1)
    @Owner("Prateek Sharma")
    @Description("TC#2 -Verify that the Booking can't be Created when the Payload is null")
    public void testCreateBookingPOST_Negetive()
    {
        //Setup and Making a Request
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response = RestAssured.given(requestSpecification).when().body("{ }").log().all().post();
        System.out.println(response.asString());

        validatableResponse =response.then().log().all();
        validatableResponse.statusCode(500);


    }
    @Test(groups = "reg",priority = 1)
    @Owner("Prateek Sharma")
    @Description("TC#2 -Verify that the Booking can't be Created when the Payload is RANDOM")
    public void testCreateBookingPOST_POSITIVE_RANDOM_DATA()
    {
        //Setup and Making a Request
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response = RestAssured.given(requestSpecification).when().body(payloadManager.createPayloadBookingFakerJS()).log().all().post();
        System.out.println(response.asString());

        validatableResponse =response.then().log().all();
        validatableResponse.statusCode(200);


    }



}
