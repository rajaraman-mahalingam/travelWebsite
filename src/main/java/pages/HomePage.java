package pages;

import core.Browser;
import core.Config;
import core.Page;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class HomePage extends Page {
    private static final String pageUrl = "";

    public static String pageUrl() {
        return pageUrl;
    }



    private class Locators {

        // Top Navigation
        private static final String topNavigationBar = "xpath=//UL[@class='nav navbar-nav navbar-left go-right']";
        private static final String home = topNavigationBar+"//A[@class='dropdown-toggle'][text()=' Home ']";
        private static final String tours = topNavigationBar+"//A[@href='http://www.phptravels.net/tours']";
        private static final String cars = topNavigationBar+"//A[@href='http://www.phptravels.net/cars']";
        private static final String visa = topNavigationBar+"//A[@href='http://www.phptravels.net/visa']";
        private static final String blog = topNavigationBar+"//A[@href='http://www.phptravels.net/blog']";
        private static final String contact = topNavigationBar+"//A[@href='http://www.phptravels.net/contact']";
        private static final String hotels = topNavigationBar+"//A[@href='http://www.phptravels.net/hotels']";
        private static final String flights = topNavigationBar+"//A[@href='http://www.phptravels.net/flights']";

        // Search
        private static final String searchGround = "xpath=//DIV[@class='searchground']";
        private static final String searchHotels = "xpath=//SPAN[@class='hidden-xs'][text()='Hotels   ']";
        private static final String searchTours = "xpath=//SPAN[@class='hidden-xs'][text()='Tours    ']";
        private static final String searchFlights = "xpath=//SPAN[@class='hidden-xs'][text()='Flights  ']";
        private static final String searchCars = "xpath=//SPAN[@class='hidden-xs'][text()='Cars     ']";
        private static final String searchVisa = "xpath=//SPAN[@class='hidden-xs'][text()='Visa     ']";

        // Featured section
        private static final String featuredHotels = "xpath=//DIV[@class='featured-back hidden-xs hidden-sm']";
        private static final String featuredHotlesItems = "xpath=(//DIV[@class='owl-item'])";





    }




}
