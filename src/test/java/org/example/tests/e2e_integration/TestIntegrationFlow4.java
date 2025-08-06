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

public class TestIntegrationFlow4 extends BaseTest {
    // Create a booking -> Update it -> Try to Delete

    @Test(groups = "qa", priority = 0)
    @Owner("Prateek Sharma")
    @Description("TC#INT1 - Step 1 - Create a Booking and Get Auth Token")
    public void createBookingAndGetAuthToken(ITestContext iTestContext) {
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);

        response = RestAssured.given(requestSpecification)
                .when()
                .body(payloadManager.createPayloadBookingAsString())
                .post();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());
        assertActions.verifyStringKey(bookingResponse.getBooking().getFirstname(), "Prateek");
        assertActions.verifyStringKeyNotNull(bookingResponse.getBookingid());

        iTestContext.setAttribute("bookingid", bookingResponse.getBookingid());
    }

    @Test(groups = "qa", priority = 1)
    @Owner("Prateek Sharma")
    @Description("TC#INT2 - Step 2 - Update the content for the created booking")
    public void testUpdateBookingByID(ITestContext iTestContext) {
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        String token = getToken();
        iTestContext.setAttribute("token", token);

        String basePathPUTPATCH = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        System.out.println("Update URL: " + basePathPUTPATCH);

        requestSpecification.basePath(basePathPUTPATCH);

        response = RestAssured.given(requestSpecification)
                .cookie("token", token)
                .body(payloadManager.fullUpdatePayloadAsString())
                .put();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        Booking booking = payloadManager.getResponseFromJSON(response.asString());
        assertActions.verifyStringKeyNotNull(booking.getFirstname());
        assertActions.verifyStringKey(booking.getFirstname(), "Prateek");
    }

    @Test(groups = "qa", priority = 2)
    @Owner("Prateek Sharma")
    @Description("TC#INT3 - Step 3 - Delete the updated booking")
    public void testDeleteBookingByID(ITestContext iTestContext) {
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        String token = (String) iTestContext.getAttribute("token");

        String basePATHDelete = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        System.out.println("Delete URL: " + basePATHDelete);
        System.out.println("Token: " + token);

        requestSpecification.basePath(basePATHDelete);

        validatableResponse = RestAssured.given(requestSpecification)
                .cookie("token", token) // ✅ Proper way to send token
                .when().delete()
                .then().log().all();

        validatableResponse.statusCode(201);
    }
}
