package core;


import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * @author Rajaraman Mahalingam
 * @version 1.0.1
 */

public enum BrowserName {
    IE11("[IE11]", "IE", Platform.WIN8_1, "11", "Internet explorer"),
    Chrome("[Chrome]", "CH", Platform.WIN8_1, "50.0", "Chrome"),
    //ChromeLinux("[Chrome]", "CH", Platform.LINUX, "50.0", "Chrome"),
    HeadLess("[Chrome]", "CH", Platform.WIN8_1, "50.0", "HeadLess");
    //HeadLessLinux("[Chrome]", "CH", Platform.LINUX, "50.0", "HeadLess");

    private final String browser;
    private final String shortName;
    private final Platform platform;
    private final String version;
    private final String sourceName;

    BrowserName(String browser, String shortName, Platform platform, String version, String sourceName) {
        this.browser = browser;
        this.shortName = shortName;
        this.platform = platform;
        this.version = version;
        this.sourceName = sourceName;
    }

    @SuppressWarnings("deprecation")
    public WebDriver createDriver() {
        try {
            switch (this) {
                case IE11:
                    return new InternetExplorerDriver(Config.getEnvironment().getCapabilities());
                case Chrome:
                    //case ChromeLinux:
                    return new ChromeDriver(Config.getEnvironment().getCapabilities());
                case HeadLess:
                    //case HeadLessLinux:
                    return new ChromeDriver(Config.getEnvironment().getCapabilities());
                default:
                    TestLogger.fatal("Cannot create Driver of type" + this);
                    return null;
            }
        } catch (Exception e) {
            TestLogger.debug("=>Cannot create any of the Driver", e);
            return null;
        }
    }
}