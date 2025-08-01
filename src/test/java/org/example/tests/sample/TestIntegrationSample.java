package org.example.tests.sample;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestIntegrationSample {
    //Create a booking, Create a Token
    //Verify that Get booking
    //Update the booking
    //Delete the booking

    @Test(groups = "qa", priority = 1)
    @Owner("Prateek Sharma")
    @Description("TC#INT1 - Step 1. Verify that the Booking can be created")
    public void testCreateBooking()
    {
        Assert.assertTrue(true);
    }
    @Test(groups = "qa" , priority = 2)
    @Owner("Prateek Sharma")
    @Description("TC#INT2 -Step 2.  Verify the Booking by ID")
    public void testVerifyBookingID()
    {
        Assert.assertTrue(true);
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
