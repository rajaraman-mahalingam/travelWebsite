package core;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.By.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;


/**
 * @author Rajaraman Mahalingam
 * @version 1.0.1
 */
public class Element {
    private final LocatorStrategy locatorStrategy;
    private final String actualLocator;

    Element(LocatorStrategy locatorStrategy, String actualLocator) {
        this.locatorStrategy = locatorStrategy;
        this.actualLocator = actualLocator;
    }

    static Element locatorIdentifier(String locator) {
        for (LocatorStrategy e : LocatorStrategy.values()) {
            if (locator.startsWith(e.name)) {
                return new Element(e, locator.substring(e.name.length(), locator.length()));
            }
        }
        if (locator.startsWith("/") || locator.startsWith("(/") || locator.startsWith("((/")) {
            return new Element(LocatorStrategy.byXpath, locator);
        }
        return new Element(LocatorStrategy.byCssPath, locator);
    }

    static By getElement(String element) {
        Element el = locatorIdentifier(element);
        try {
            switch (el.locatorStrategy) {
                case byClassName:
                    TestLogger.debug("Found Locator by Class Name");
                    return new ByClassName(element);
                case byId:
                    TestLogger.debug("Found Locator by ID");
                    return new ById(element);
                case byXpath:
                    TestLogger.debug("Locating by Xpath:" + element);
                    return new ByXPath(element);
                case byName:
                    TestLogger.debug("Found Locator by Name");
                    return new ByName(element);
                case byLinkText:
                    TestLogger.debug("Found Locator by Link Text");
                    return new ByLinkText(element);
                case byTagName:
                    TestLogger.debug("Found Locator by Tag Name");
                    return new ByTagName(element);
                case byPartialLinkText:
                    TestLogger.debug("Found Locator by Partial Link Text");
                    return new ByPartialLinkText(element);
                case byCssPath:
                    TestLogger.debug("Found Locator by Css");
                    return new ByCssSelector(element);
                default:
                    return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            TestLogger.fatal("Unable to Locate Element.. Check the Locator string!");
            return null;
        }
    }

    public enum LocatorStrategy {
        byClassName("className="), byId("id="), byXpath("xpath="), byCssPath("css="), byName("name="), byLinkText(
                "linktext="), byTagName("tagname="), byPartialLinkText("partiallinktext=");

        final String name;

        LocatorStrategy(String name) {
            this.name = name;
        }
    }

    public static WebElement findElement(String Locator) {
        return Browser.driver().findElement(Element.getElement(Element.locatorIdentifier(Locator).actualLocator));
    }

    public static List<WebElement> findElements(String Locator) {
        return Browser.driver().findElements(Element.getElement(Element.locatorIdentifier(Locator).actualLocator));
    }

    public static void click(String Locator) {
        findElement(Locator).click();
    }

    public static void clear(String Locator) {
        findElement(Locator).clear();
    }

    public static void submit(String Locator) {
        findElement(Locator).submit();
    }

    public static void select(String Locator) {
        Select select = new Select(findElement(Locator));
    }

    public static void waitForElementToBeVisible(String locator) {
        Page.waitForElement().until(ExpectedConditions.visibilityOf(Element.findElement(locator)));
    }

    public static void waitForElementToBeClickable(String locator) {
        Page.waitForElement().until(ExpectedConditions.elementToBeClickable(Element.findElement(locator)));
    }

    public static void waitForElementTobeSelected(String locator) {
        Page.waitForElement().until(ExpectedConditions.elementToBeSelected(Element.findElement(locator)));
    }

//    public static void waitForElementToBeVisible(String element) {
//        Browser.waitDriver().until(ExpectedConditions.visibilityOf(findElement(element)));
//    }
//
//    public static  void waitForElementTobeClickable(String element) {
//        Browser.waitDriver().until(ExpectedConditions.elementToBeClickable(findElement(element)));
//    }

    public static boolean isElementDisplayed(String Locator) {
        try {
            findElement(Locator).isDisplayed();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public static boolean isElementSelected(WebElement Locator) {
        return findElement(Locator.toString()).isSelected();
    }

    public static boolean isElementEnabled(WebElement Locator) {
        try {
            findElement(Locator.toString()).isEnabled();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public static boolean isTextDisplayed(String text) {
        List<WebElement> list = findElements("//*[contains(text(),'" + text + "')]");
        return list.size() > 0;
    }

    public static void typeIn(String Locator, String keys) {
        findElement(Locator).clear();
        findElement(Locator).sendKeys(keys);
    }

    public static String getText(String Locator) {
        return findElement(Locator).getText();
    }

    public static int getSize(String Locator) { return findElements(Locator).size();}

}
