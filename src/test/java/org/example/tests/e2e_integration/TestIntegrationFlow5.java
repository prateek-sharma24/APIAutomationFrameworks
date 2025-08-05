package org.example.tests.e2e_integration;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.example.base.BaseTest;
import org.example.endpoints.APIConstants;
import org.example.pojos.request.Booking;
import org.example.pojos.response.BookingResponse;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class TestIntegrationFlow5 extends BaseTest {

    // 1. Create Booking
    @Test(groups = "qa", priority = 0)
    @Owner("Prateek Sharma")
    @Description("TC#INT5 - Step 1 - Create a Booking and Get Auth Token")
    public void testCreateBooking(ITestContext iTestContext) {
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);

        response = RestAssured.given(requestSpecification)
                .body(payloadManager.createPayloadBookingAsString())
                .post();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());
        assertActions.verifyStringKeyNotNull(bookingResponse.getBookingid());

        iTestContext.setAttribute("bookingid", bookingResponse.getBookingid());

        // Generate and store token
        String token = getToken();  // Assuming this uses /auth
        iTestContext.setAttribute("token", token);
    }

    // 2. Delete Booking
    @Test(groups = "qa", priority = 1)
    @Owner("Prateek Sharma")
    @Description("TC#INT4 - Step 2 - Delete the Booking by ID")
    public void testDeleteBookingByID(ITestContext iTestContext) {
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        String token = (String) iTestContext.getAttribute("token");

        String basePathDelete = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;

        requestSpecification.basePath(basePathDelete)
                .header("Cookie", "token=" + token); // ✅ correct format

        validatableResponse = RestAssured.given()
                .spec(requestSpecification)
                .when().delete()
                .then().log().all();

        validatableResponse.statusCode(201); // Use the correct expected code
    }

    // 3. Try to Update Deleted Booking
    @Test(groups = "qa", priority = 2)
    @Owner("Prateek Sharma")
    @Description("TC#INT3 - Step 3 - Try to update a deleted booking and verify the response")
    public void testUpdateBookingByID(ITestContext iTestContext) {
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        String token = getToken();
        iTestContext.setAttribute("token", token);

        String basePATHUpdate = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        System.out.println("Attempting to update deleted booking at: " + basePATHUpdate);

        response = RestAssured
                .given(requestSpecification)
                .cookie("token", token)
                .body(payloadManager.fullUpdatePayloadAsString())
                .put(basePATHUpdate);

        // ✅ Add logging here
        System.out.println("Update booking status code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.asString());

        validatableResponse = response.then().log().all();

        // ❗ Adjust the status code check to match actual API behavior
        // Either:
        validatableResponse.statusCode(404); // If the API returns 405
        // Or handle both cases:
        // Assert.assertTrue(response.getStatusCode() == 404 || response.getStatusCode() == 405,
        //     "Expected status 404 or 405, but got " + response.getStatusCode());
    }

}
