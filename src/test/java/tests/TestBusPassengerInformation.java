package tests;

import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.bus.BusHomePage;
import pages.bus.PaymentPage;
import pages.bus.TripsPage;

import java.util.HashMap;

@Listeners({TestListener.class})
public class TestBusPassengerInformation extends BaseTest {
    @Test
    public void testBusPassengerInformation() {
        BusHomePage busHomePage = new BusHomePage();
        Assert.assertTrue(busHomePage.isAt(), "Bus home page is not loading!");
        busHomePage.searchTrips("İstanbul Avrupa", "Ankara");

        TripsPage tripsPage = new TripsPage();
        Assert.assertTrue(tripsPage.isAt(), "Trips page is not loading!");
        HashMap<String, String> journeyInformation = tripsPage.selectJourney();
        HashMap<String, String> passengerInformation = tripsPage.selectSeat();
        tripsPage.redirectPayStep();

        PaymentPage paymentPage = new PaymentPage();
        Assert.assertTrue(paymentPage.isAt(), "Payment page is not loading!");
        HashMap<String, String> paymentSummary = paymentPage.getTripSummary();
        Assert.assertEquals(paymentSummary.get("origin"),
                journeyInformation.get("departureBusTerminal"), "Departure terminal name is wrong!");
        Assert.assertEquals(paymentSummary.get("destination"),
                journeyInformation.get("arrivalBusTerminal"), "Arrival terminal name is wrong!");
        Assert.assertEquals(paymentSummary.get("seatNumber"),
                passengerInformation.get("seatNumber"), "Passenger seat number is wrong!");
        Assert.assertEquals(paymentSummary.get("totalAmount"),
                passengerInformation.get("totalSeatsPrice"), "Total seat price is wrong!");
    }
}
