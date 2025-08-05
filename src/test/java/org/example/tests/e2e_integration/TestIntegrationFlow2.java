package org.example.tests.e2e_integration;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.example.base.BaseTest;
import org.example.endpoints.APIConstants;
import org.example.pojos.response.BookingResponse;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class TestIntegrationFlow2 extends BaseTest {
    // Flow: Create Booking -> Delete it -> Verify Deletion

    @Test(groups = "qa", priority = 1)
    @Owner("Prateek Sharma")
    @Description("TC#INT1 - Step 1. Verify that the Booking can be created")
    public void testCreateBooking(ITestContext iTestContext) {
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);

        response = RestAssured.given(requestSpecification)
                .when().body(payloadManager.createPayloadBookingAsString())
                .post();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());
        assertActions.verifyStringKey(bookingResponse.getBooking().getFirstname(), "Prateek");
        assertActions.verifyStringKeyNotNull(bookingResponse.getBookingid());

        iTestContext.setAttribute("bookingid", bookingResponse.getBookingid());
    }

    @Test(groups = "qa", priority = 2)
    @Owner("Prateek Sharma")
    @Description("TC#INT4 - Step 2. Delete the Booking by ID")
    public void testDeleteBookingByID(ITestContext iTestContext) {
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        String token = getToken(); // Generate token for deletion

        iTestContext.setAttribute("token", token);

        String basePathDelete = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;

        requestSpecification.basePath(basePathDelete).header("Cookie", "token=" + token);

        validatableResponse = RestAssured.given().spec(requestSpecification)
                .when().delete().then().log().all();

        validatableResponse.statusCode(201); // Confirm deletion
    }

    @Test(groups = "qa", priority = 3)
    @Owner("Prateek Sharma")
    @Description("TC#INT2 - Step 3. Verify that deleted booking is not accessible")
    public void testVerifyBookingID(ITestContext iTestContext) {
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        String basePathGET = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;

        requestSpecification.basePath(basePathGET);

        response = RestAssured.given(requestSpecification).when().get();
        validatableResponse = response.then().log().all();

        // After deletion, the GET request should return 404 Not Found
        validatableResponse.statusCode(404);
    }
}
