package core;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.configuration2.ex.ConfigurationException;

/**
 * @author Rajaraman Mahalingam
 * @version 1.0.1
 */

public class Config {
    private static BrowserEnvironment environment;
    private static BrowserName browserName;
    private static String baseURL;
    private static String platform;
    private static String gridURL;
    private static String userName;
    private static String passWord;
    private static boolean debug;
    private static String runCondition;
    private static String priority;

    @Getter
    @Setter
    private static String testName;

    @Getter
    @Setter
    private static String testClass;

    private static String readAutomationProperty(String key) throws ConfigurationException {
        String value = System.getenv(key);
        if (value != null) {
            Utilities.readAutomationPropertiesFromEnvironmentVariables(key, value);
        }
        if (value == null) {
            value = System.getProperty(key);
            if (value != null) {
                Utilities.readAutomationPropertiesFromEnvironmentVariables(key, value);
            }
        }
        if (value == null) {
            value = Utilities.readAutomationPropertiesFromFileUsingKey(key);
        }
        return value;
    }

    public static String getBaseURL() throws ConfigurationException {
        return baseURL = readAutomationProperty("baseURL");
    }

    public static BrowserEnvironment getEnvironment() throws ConfigurationException {
        return environment = BrowserEnvironment.valueOf(readAutomationProperty("environment"));
    }

    public static String getPlatform() throws ConfigurationException {
        return platform = readAutomationProperty("platform");
    }

    public static BrowserName getBrowserName() throws ConfigurationException {
        return browserName = BrowserName.valueOf(readAutomationProperty("browserName"));
    }

// --Commented out by Inspection START (06/10/2017 14:09):
//    public static String getGridURL() throws ConfigurationException {
//        return gridURL = readAutomationProperty("gridURL");
//    }
// --Commented out by Inspection STOP (06/10/2017 14:09)

    public static String getUserName() throws ConfigurationException {
        return userName = readAutomationProperty("username");
    }

    public static String getPassword() throws ConfigurationException {
        return passWord = readAutomationProperty("password");
    }

    public static boolean isDebug() throws ConfigurationException {
        //readAutomationProperty();
        return debug = Boolean.parseBoolean(readAutomationProperty("isDebug"));
    }

    public static String getRunCondition() throws ConfigurationException {
        return runCondition = readAutomationProperty("runCondition");
    }

    public static String getPriority() throws ConfigurationException {
        return priority = readAutomationProperty("priority");
    }

    public static String getTestDescription() throws ClassNotFoundException, NoSuchMethodException {
        String description;
        description = TestRunManager.getTestDescription();
        if (description == null) {
            description = "Error searching for test description !!!";
        }
        else if (description.equals("")) {
            description = "Test description is empty !!!";
        }
        return description;
    }

    public static String getTestRunCondition() throws ClassNotFoundException, NoSuchMethodException {
        String description;
        description = TestRunManager.getTestRunConditon();
        if (description == null) {
            description = "Run Condition Not Set. Will execute on all Test Runs!";
        }
        else if (description.equals("")) {
            description = "Run Condition is empty. Will execute on all Test Runs!  !!!";
        }
        return description;
    }

    public static String getTestPriority() throws ClassNotFoundException, NoSuchMethodException {
        String description;
        description = TestRunManager.getTestPriority();
        if ((description == null) || (description.equals(""))) {
            description = "Priority Not set. Will execute on all Test Runs!";
        }
        return description;
    }

    public static boolean verifyRunCondition() throws ClassNotFoundException, NoSuchMethodException, ConfigurationException {
        if ((getRunCondition().equalsIgnoreCase("All")) || (getRunCondition().equalsIgnoreCase(""))) {
            return true;
        }
        return getRunCondition().equalsIgnoreCase(getTestRunCondition());
    }

    public static boolean verifyPriority() throws ClassNotFoundException, NoSuchMethodException, ConfigurationException {
        if ((getRunCondition().equalsIgnoreCase("All")) || (getRunCondition().equalsIgnoreCase("Sanity"))
                || ((getRunCondition().equalsIgnoreCase("Regression")) && (getPriority().equalsIgnoreCase("")))
                || (getRunCondition().equalsIgnoreCase("") && (getPriority().equalsIgnoreCase("")))) {
            return true;
        }
        else if ((getRunCondition().equalsIgnoreCase("Regression")) && (!getPriority().equalsIgnoreCase(""))
                || (getRunCondition().equalsIgnoreCase("")) && (!getPriority().equalsIgnoreCase(""))) {
            return getPriority().equalsIgnoreCase(getTestPriority());
        }
        return false;
    }
}
