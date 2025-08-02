package org.example.tests.crud;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.example.base.BaseTest;
import org.example.endpoints.APIConstants;
import org.example.modules.PayloadManager;
import org.testng.annotations.Test;

public class TestCreateToken extends BaseTest {

    @Test(groups = "reg" , priority = 1)
    @Owner("Prateek")
    @Description("TC#2 - Create Token and Verify")
    public void testTokenPOST()
    {
        //Prep of Request
        requestSpecification.basePath(APIConstants.AUTH_URL);

        //Making of the Request
        response = RestAssured.given(requestSpecification).when().body(payloadManager.setAuthPayload()).post();

        //Extraction (JSON String Response to JAVA Object)
        String token = payloadManager.getTokenFromJSON(response.asString());
        System.out.println(token);

        //Validation of the request
        assertActions.verifyStringKeyNotNull(token);
    }

}
