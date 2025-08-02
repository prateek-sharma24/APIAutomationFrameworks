package org.example.asserts;
import io.restassured.response.Response;

import static org.testng.Assert.assertEquals;
import static org.assertj.core.api.Assertions.*;


public class AssertActions {
    //Common Type of assertions are kept here
    //In order to have reusability of code functions are created
    public void verifyResponseBody(String actual,String expected,String description)
    {
        assertEquals(actual,expected,description);
    }
    public void verifyResponseBody(int actual, int expected,String description)
    {
        // method overloading function
        assertEquals(actual,expected,description);
    }
    public void verifyStatusCode(Response response,Integer expected)
    {
        assertEquals(response.getStatusCode(),expected);
    }
    public void verifyStringKey(String keyExpect,String keyActual)
    {
        //AssertJ
        assertThat(keyExpect).isNotNull();
        assertThat(keyExpect).isNotBlank();
        assertThat(keyExpect).isEqualTo(keyActual);
    }
    public void verifyStringKeyNotNull(Integer keyExpect)
    {
        //AssertJ
        assertThat(keyExpect).isNotNull();
    }
    public void verifyStringKeyNotNull(String keyExpect)
    {
        //AssertJ
        assertThat(keyExpect).isNotNull();

    }
}
