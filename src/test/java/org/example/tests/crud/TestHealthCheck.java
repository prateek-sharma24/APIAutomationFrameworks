package org.example.tests.crud;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.example.base.BaseTest;
import org.example.endpoints.APIConstants;
import org.testng.annotations.Test;

public class TestHealthCheck extends BaseTest {

    @Test(groups = "reg", priority = 1)
    @Owner("Prateek Sharma")
    @Description("TC#3 -Verify Health")
    public  void testGETHealthCheck()
    {
        requestSpecification.basePath(APIConstants.PING_URL);
        response = RestAssured.given(requestSpecification).when().get();
        validatableResponse =response.then().log().all();
        validatableResponse.statusCode(201);

    }
}
