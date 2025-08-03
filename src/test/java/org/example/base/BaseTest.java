package org.example.base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.example.asserts.AssertActions;
import org.example.endpoints.APIConstants;
import org.example.modules.PayloadManager;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseTest {
    //CommonToAll Testcase
    //  // Base URL, Content Type - json - common
    //This file contains functions of request which need not to be manipulated
    public RequestSpecification requestSpecification;
    public AssertActions assertActions;
    public PayloadManager payloadManager;
    public JsonPath jsonPath;
    public Response response;
    public ValidatableResponse validatableResponse;


    @BeforeTest
    public void setup()
    {
        System.out.println("Starting of the test");
        payloadManager =new PayloadManager();
        assertActions = new AssertActions();
        //The request will need to be added in every test case,
       // requestSpecification = RestAssured.given();
       // requestSpecification.baseUri(APIConstants.BASE_URL);
        //requestSpecification.contentType(ContentType.JSON).log().all();
        //This request can be witten with builder class as well
        requestSpecification =new RequestSpecBuilder()
                .setBaseUri(APIConstants.BASE_URL)
                .addHeader("Content-Type","application/json")
                .build().log().all();
    }
    public String getToken()
    {
        requestSpecification =RestAssured.given();
        requestSpecification.baseUri(APIConstants.BASE_URL)
                .basePath(APIConstants.AUTH_URL);
        //Setting the payload
        String payload = payloadManager.setAuthPayload();
        //Get Token
        response =requestSpecification.contentType(ContentType.JSON).body(payload).when().post();
        String token =payloadManager.getTokenFromJSON(response.asString());
        return token;
    }
    @AfterTest
    public void tearDown()
    {
        System.out.println("Finished the test");
    }
}
