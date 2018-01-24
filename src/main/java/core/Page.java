package core;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.util.concurrent.TimeUnit;

/**
 * @author Rajaraman Mahalingam
 * @version 1.0.1
 */
public class Page {

     public static void open(String url) throws ConfigurationException {
        Browser.openPage(Config.getBaseURL() + url);
        Browser.waitForDOMToLoad(5);
    }

    public static Wait waitForElement() {
         Wait waitForElement = new FluentWait(Browser.driver())
                 .withTimeout(30, TimeUnit.SECONDS)
                 .pollingEvery(5,TimeUnit.SECONDS)
                 .ignoring(NoSuchElementException.class);
         return waitForElement;
    }

    public static void isPageDisplayed(String expectedPage, String actualPage) throws ConfigurationException {
         TestLogger.assertEquals("Expected Page displayed", expectedPage,actualPage);
    }

    public static String getPageURL() throws ConfigurationException {
         return Browser.getPageURL();
    }

//    public static void


}
