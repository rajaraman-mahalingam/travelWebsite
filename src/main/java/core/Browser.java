package core;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Rajaraman Mahalingam
 * @version 1.0.1
 */

public class Browser {

    public static WebDriver driver;
    public static ThreadLocal<WebDriver> threadDriver;

    static void openBrowser() {
        /*TestLogger.debug("Executing: openBrowser()");
        threadDriver = new ThreadLocal<WebDriver>() {
            @Override
            protected WebDriver initialValue() {
                return createDriver();
            }
        };
        driver=threadDriver.get();*/
        driver = createDriver();
    }

    static void closeBrowser() {
        TestLogger.debug("Executing: closeBrowser()");
        driver.quit();
    }

    private static WebDriver createDriver() {
        WebDriver returnedDriver = null;
        try {
            switch (getEnvironment()) {
                case Local:
                    returnedDriver = getBrowserType().createDriver();
                    returnedDriver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
                    break;
                /*case Grid:
                     returnedDriver = (RemoteWebDriver)
                     BrowserEnvironment.Remote.getRemoteCapabilities(getBrowserType());
                    break;*/
                default:
                    TestLogger.fatal(
                            "=>Unable to create browser instance. Please check automation.properties file for the correct values..");
                    returnedDriver = null;
                    break;
            }
            return returnedDriver;
        } catch (Throwable e) {
            TestLogger.debug(
                    "=>Unable to create browser instance. Please check automation.properties file for the correct values..");
            return null;
        }
    }

    public static WebDriver driver() {
        return driver;
    }

    private static BrowserEnvironment getEnvironment() throws ConfigurationException {
        return Config.getEnvironment();
    }

    private static BrowserName getBrowserType() throws ConfigurationException {
        return Config.getBrowserName();
    }

    public static void openPage(String pageURL) {
        TestLogger.info("Executing openPage(" + pageURL + ")");
        try {
            driver.get(pageURL);
        } catch (Throwable e) {
            TestLogger.debug("Error Opening page", e);
        }
    }

    public static WebDriverWait waitDriver() {
        WebDriverWait webDriverWait = new WebDriverWait(Browser.driver, 20);
        return webDriverWait;
    }

    public static void back() {
        driver.navigate().back();
    }

    public static void navigate(String url) {
        driver.navigate().to(url);
    }

    public static void scrollDown() {
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0,600)", "");
    }

    public static void scrollUp() {
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(600,0)", "");
    }


    public static void close() {
        driver.close();
    }

    public static void refresh() {
        driver.navigate().refresh();
    }

    public static void waitForDOMToLoad(long unit) {
        driver.manage().timeouts().implicitlyWait(unit, TimeUnit.SECONDS);
    }

    public static String getPageTitle() {
        return driver.getTitle();
    }

    public static String getPageURL() {
        return driver.getCurrentUrl();
    }

    public static void setPageWait(long wait) {
        try {
            driver.manage().wait(wait);
        } catch (InterruptedException e) {
            TestLogger.debug("Unable to set wait", e);
        }
    }

    public void getPageCookies() {
        driver.manage().getCookies();
    }

    public void addPageCookies(Cookie cookie) {
        driver.manage().addCookie(cookie);
    }

    public void deletePageCookies(Cookie cookie) {
        driver.manage().deleteCookie(cookie);
    }

    public void deleteSpecificCookie(Cookie cookie) {
        driver.manage().deleteCookie(cookie);
    }

    public void getPageSource() {
        driver.getPageSource();
    }

    public void switchToParentFrame() {
        driver.switchTo().parentFrame();
    }

    public void switchToSpecificFrameByName(String frame) {
        driver.switchTo().frame(frame);
    }

    public void switchToSpecificFrameById(int id) {
        driver.switchTo().frame(id);
    }

    public String getPageWindowHandle() {
        return driver.getWindowHandle();
    }

    public Set<String> getPageAllWindowHandle() {
        return driver.getWindowHandles();
    }
}
