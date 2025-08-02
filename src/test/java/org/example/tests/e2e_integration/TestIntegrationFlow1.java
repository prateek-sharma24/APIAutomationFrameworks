package org.example.tests.e2e_integration;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.example.base.BaseTest;
import org.example.endpoints.APIConstants;
import org.example.pojos.request.Booking;
import org.example.pojos.response.BookingResponse;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class TestIntegrationFlow1 extends BaseTest {

    //TestE2EFlow_01

    //Test E2E Scenario

    // 1.Create a booking -> bookingID
    // 2. Create token ->token
    //3. Verify that the create booking is working -GET Request to bookingID
    //4. Update the booking (bookingID , Token)-Need to get the token, bookingID
    //5.Delete the booking -Need to get the token, BookingID from above request

    @Test(groups = "qa", priority = 1)
    @Owner("Prateek Sharma")
    @Description("TC#INT1 - Step 1. Verify that the Booking can be created")
    public void testCreateBooking(ITestContext iTestContext)
    {
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response = RestAssured.given(requestSpecification)
                .when().body(payloadManager.createPayloadBookingAsString())
                .post();
        validatableResponse =response.then().log().all();
        validatableResponse.statusCode(200);
        BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());
        assertActions.verifyStringKey(bookingResponse.getBooking().getFirstname(),"Prateek");
        assertActions.verifyStringKeyNotNull(bookingResponse.getBookingid());

        iTestContext.setAttribute("bookingid", bookingResponse.getBookingid());


    }
    @Test(groups = "qa" , priority = 2)
    @Owner("Prateek Sharma")
    @Description("TC#INT2 -Step 2.  Verify the Booking by ID")
    public void testVerifyBookingID(ITestContext iTestContext)
    {
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        String basePathGET =APIConstants.CREATE_UPDATE_BOOKING_URL+"/"+bookingid;
        System.out.println(basePathGET);

        requestSpecification.basePath(basePathGET);
        response =RestAssured.given(requestSpecification).when().get();
        validatableResponse =response.then().log().all();
        validatableResponse.statusCode(200);

    }
    @Test(groups = "qa" , priority =3)
    @Owner("Prateek Sharma")
    @Description("TC#INT3 -Step 3- Verify the Updated Booking by ID")
    public void testUpdateBookingByID()
    {
        Assert.assertTrue(true);
    }
    @Test(groups = "qa" , priority = 3)
    @Owner("Prateek Sharma")
    @Description("TC#INT4 -Step 4- Delete the Booking by ID")
    public void testDeleteBookingByID()
    {
        Assert.assertTrue(true);
    }





}



