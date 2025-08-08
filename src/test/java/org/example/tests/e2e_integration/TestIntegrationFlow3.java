package org.example.tests.e2e_integration;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.example.base.BaseTest;
import org.example.endpoints.APIConstants;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class TestIntegrationFlow3 extends BaseTest {
    //Get a booking from Get All ->Try to delete that booking
    @Test(groups = "qa", priority = 0)
    @Owner("Prateek Sharma")
    @Description("TCINT#1 -Get booking from list of bookings")
    public void testVerifyGetAllBookingId(ITestContext iTestContext)
    {
        Integer bookingid = getBookingID();
        iTestContext.setAttribute("bookingid",bookingid);
        validatableResponse =response.then().log().all();
        //Validatable Assertions
        validatableResponse.statusCode(200);

    }
    @Test(groups = "qa", priority = 1)
    @Owner("Prateek Sharma")
    @Description("TCINT#2 -Delete booking by ID")
    public void testDeleteBookingById(ITestContext iTestContext)
    {
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        String token =getToken();
        iTestContext.setAttribute("token", token);
        String basePathDELETE = APIConstants.CREATE_UPDATE_BOOKING_URL+"/"+bookingid;
        requestSpecification.basePath(basePathDELETE).cookie("token",token);
        validatableResponse = RestAssured.given().spec(requestSpecification)
                .when().delete().then().log().all();
        //Validatable Assertions
        validatableResponse.statusCode(201);


    }



}
