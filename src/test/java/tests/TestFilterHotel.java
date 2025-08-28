package tests;

import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.hotel.HotelHomePage;
import pages.hotel.HotelsPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Listeners({TestListener.class})
public class TestFilterHotel extends BaseTest {

    @Test
    public void testFilterHotel() {
        HotelHomePage hotelHomePage = new HotelHomePage();
        Assert.assertTrue(hotelHomePage.isAt(), "Hotel home page is not loading!");
        hotelHomePage.searchHotel("İstanbul");

        HotelsPage hotelsPage = new HotelsPage();
        Assert.assertTrue(hotelsPage.isAt(), "Hotels page is not loading!");
        hotelsPage.filterHalfBoard();
        HashMap<String, String> counts = hotelsPage.getHotelCount();
        Assert.assertEquals(counts.get("filterCount"),
                counts.get("resultCount"), "Filtered item count is wrong!");
        boolean isHalfBoard = hotelsPage.checkHalfBoard();
        Assert.assertTrue(isHalfBoard, "Half board filter result is wrong!");
        hotelsPage.sortAscendingHotels();
        List<Integer> actualPrices = hotelsPage.getHotelPrices();
        List<Integer> expectedPrices = new ArrayList<>(actualPrices);
        Collections.sort(expectedPrices);
        Assert.assertEquals(actualPrices, expectedPrices, "Prices are not in ascending order!");
    }
}
