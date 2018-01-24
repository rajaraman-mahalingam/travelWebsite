package core;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.stream.IntStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;

/**
 * @author Rajaraman Mahalingam
 * @version 1.0.1
 */

public class Utilities {
    public static final Properties prop = new Properties();
    public static final String filePath = "src/main/resources/automation.properties";
    public static InputStream input = null;

    public static void takeScreenShot() {
        try {
            File Screenshot = ((TakesScreenshot) Browser.driver()).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(Screenshot,
                    new File("./target/screen-shots/" + Config.getTestName() + ".jpg"));
        } catch (Throwable e) {
            TestLogger.debug("Unable to get Screen shot.. Check Config", e);
        }
    }

//    public static String getDate(String value) {
//        DateFormat dtf = new SimpleDateFormat("dd-MMM-yyyy");
//        LocalDate from;
//        LocalDate to;
//        int difference;
//        Calendar date = Calendar.getInstance();
//        switch (value) {
//            case "Today":
//                date.add(Calendar.DATE, 0);
//                break;
//            case "Yesterday":
//                date.add(Calendar.DATE, -1);
//                break;
//            case "Last 7 Days":
//                date.add(Calendar.DATE, -6);
//                break;
//            case "Last 30 Days":
//                date.add(Calendar.DATE, -29);
//                break;
//            case "This Month":
//                from = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
//                to = LocalDate.now();
//                difference = to.getDayOfMonth() - from.getDayOfMonth();
//                date.add(Calendar.DATE, -difference);
//                break;
//            case "Last Month":
//                break;
//            case "Last Year":
//                date.add(Calendar.DATE, -365);
//                break;
//            case "Custom Range":
//                break;
//        }
//        return dtf.format(date.getTime());
//    }

//    public static String setStartDate(String value) {
////        LocalDate from = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
////        if (value.equalsIgnoreCase("Last Month")) {
////            from = from.minusMonths(1);
////        }
////        return from.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
////    }
////
////    public static String setEndDate(String value) {
////        LocalDate to = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
////        if (value.equalsIgnoreCase("Last Month")) {
////            to = to.minusMonths(1);
////        }
////        return to.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
////    }

    public static void keyPress(Keys key) {
        Actions action = new Actions(Browser.driver());
        action.sendKeys(key).build().perform();
    }

    public static void buttonPress(String Locator) {
        Actions action = new Actions(Browser.driver());
        action.click(Element.findElement(Locator)).build().perform();
    }

    public static void readAutomationPropertiesFromFile() {
        try {
            input = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            TestLogger.fatal("Properties file Not found", e);
        }
        try {
            prop.load(input);
        } catch (IOException e) {
            TestLogger.fatal("Unable to load properties file", e);
        }
    }

    public static void readAutomationPropertiesFromEnvironmentVariables(String key, String value)
            throws ConfigurationException {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
                PropertiesConfiguration.class).configure(params.properties().setFileName(Utilities.filePath));
        Configuration config = builder.getConfiguration();
        config.setProperty(key, value);
        if (config.containsKey("password")) {
            config.clearProperty("password");
        }
        builder.save();
    }

    public static void mouseHover(String Locator) {
        Actions action = new Actions(Browser.driver());
        action.moveToElement(Element.findElement(Locator)).build().perform();
    }


    public static String getProperty(String property) {
        return prop.getProperty(property).trim();
    }

    public static double convertStringToNumber(String element) {
        return Double.parseDouble(element);
    }

    public static String getSubString(String element, int start, int end) {
        return element.substring(start, end);
    }

    public static int randomize(int number) {
        int value = 0;
        Random rand = new Random();
        while (value == 0) {
            value = rand.nextInt(number);
        }
        return value;
    }

    public static IntStream randomize(int start, int end) {
        Random rand = new Random();
        return rand.ints(start, end);
    }

    public static String randomize() {
        String generatedString = RandomStringUtils.randomAlphabetic(5);
        return generatedString;
    }
    public static String randomize(List<WebElement> a) {
        String value;
        Random rand = new Random();
        value = a.get(rand.nextInt(a.size())).getText();
        return value;
    }

    public static String readAutomationPropertiesFromFileUsingKey(String key) {
        try {
            input = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            TestLogger.fatal("Properties file Not found", e);
        }
        try {
            prop.load(input);
        } catch (IOException e) {
            TestLogger.fatal("Unable to load properties file", e);
        }
        return prop.getProperty(key).trim();
    }

}
