package home;

import core.*;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.Test;
import pages.HomePage;

public class HomePageTest extends Automation {

    @Test
    @TestDescription("Scenario: Access Home Page when user opens the http://www.phptravels.net/")
    @RunCondition("Sanity")
    @Priority("High")

    public void Access_Home_Page_when_user_opens_the_defaultUrl() throws ConfigurationException {
        TestLogger.info("Given the user opens the browser");
        Page.open(HomePage.pageUrl());

        TestLogger.info("Then the user should be redirected to the homepage");
        Page.isPageDisplayed(Config.getBaseURL(),HomePage.getPageURL());
    }
}
