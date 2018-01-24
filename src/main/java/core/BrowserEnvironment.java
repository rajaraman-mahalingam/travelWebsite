package core;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * @author Rajaraman Mahalingam
 * @version 1.0.1
 */
public enum BrowserEnvironment {

    Local("Local"), Grid("Grid"), External("External");

    public final String name;
    public String chromeDriverPath;

    BrowserEnvironment(String name) {
        this.name = name;
    }

    public DesiredCapabilities getCapabilities() {
        try {
            switch (this) {
                case Local:
                default:
                    return getLocalCapabilities();
                case Grid:
                    return getGridCapabilities();
                case External:
                    return getExternalCapabilities();
            }
        } catch (Exception e) {
            TestLogger.debug("Unable to get Capabilities", e);
            return null;
        }
    }

    private DesiredCapabilities getExternalCapabilities() {
        // TODO Auto-generated method stub
        return null;
    }

    public DesiredCapabilities getLocalHeadLessCapabilities() throws ConfigurationException {
        DesiredCapabilities caps = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        if (Config.getPlatform().equals("Windows")) {
            chromeDriverPath = "src\\main\\resources\\drivers\\chromedriver.exe";
        }
        else if (Config.getPlatform().equals("Mac")) {
            chromeDriverPath = "src/main/resources/drivers/chromedrivermac";
        }
        else if (Config.getPlatform().equals("Linux")) {
            chromeDriverPath = "src/main/resources/drivers/chromedriverLinux";
            options.addArguments("--kiosk");
        }
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        caps.setCapability(ChromeOptions.CAPABILITY, options);
        return caps;
    }

    public DesiredCapabilities getLocalChromeCapabilities() throws ConfigurationException {
        DesiredCapabilities caps = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        if (Config.getPlatform().equals("Windows")) {
            chromeDriverPath = "src\\main\\resources\\drivers\\chromedriver.exe";
            options.addArguments("--start-maximised");
            options.addArguments("--start-fullscreen");
        }
        else if (Config.getPlatform().equals("Mac")) {
            chromeDriverPath = "src/main/resources/drivers/chromedrivermac";
        }
        else if (Config.getPlatform().equals("Linux")) {
            chromeDriverPath = "src/main/resources/drivers/chromedriverLinux";
            options.addArguments("--kiosk");
            options.addArguments("--start-fullscreen");
        }
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        options.addArguments("chrome.switches", "--disable-extensions");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--incognito");
        caps.setCapability(ChromeOptions.CAPABILITY, options);
        return caps;
    }

    public DesiredCapabilities getLocalIECapabilities() {
        DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
        System.setProperty("webdriver.ie.driver", "src\\main\\resources\\drivers\\IEDriverServer.exe");
        //caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        caps.setCapability(InternetExplorerDriver.BROWSER_ATTACH_TIMEOUT, 20000);
        caps.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
        caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
        caps.setCapability(InternetExplorerDriver.ELEMENT_SCROLL_BEHAVIOR, true);
        caps.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
        caps.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
        caps.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
        caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        return caps;
    }

    public DesiredCapabilities getGridHeadLessCapabilities() throws ConfigurationException {
        DesiredCapabilities caps = DesiredCapabilities.chrome();
        if (Config.getPlatform().equals("Windows")) {
            chromeDriverPath = "src\\main\\resources\\drivers\\chromedriver.exe";
        }
        else if (Config.getPlatform().equals("Mac")) {
            chromeDriverPath = "src/main/resources/drivers/chromedrivermac";
        }
        else if (Config.getPlatform().equals("Linux")) {
            chromeDriverPath = "src/main/resources/drivers/chromedriverLinux";
        }
        ChromeOptions options = new ChromeOptions();
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--kiosk");
        caps.setCapability(ChromeOptions.CAPABILITY, options);
        return caps;
    }

    public DesiredCapabilities getGridChromeCapabilities() throws ConfigurationException {
        DesiredCapabilities caps = DesiredCapabilities.chrome();
        if (Config.getPlatform().equals("Windows")) {
            chromeDriverPath = "src\\main\\resources\\drivers\\chromedriver.exe";
        }
        else if (Config.getPlatform().equals("Mac")) {
            chromeDriverPath = "src/main/resources/drivers/chromedrivermac";
        }
        else if (Config.getPlatform().equals("Linux")) {
            chromeDriverPath = "src/main/resources/drivers/chromedriverLinux";
        }
        ChromeOptions options = new ChromeOptions();
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        options.addArguments("chrome.switches", "--disable-extensions");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--kiosk");
        caps.setCapability(ChromeOptions.CAPABILITY, options);
        return caps;
    }

    public DesiredCapabilities getGridIECapabilities() {
        DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
        System.setProperty("webdriver.ie.driver", "src\\main\\resources\\drivers\\IEDriverServer.exe");
        caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        caps.setCapability(InternetExplorerDriver.BROWSER_ATTACH_TIMEOUT, 20000);
        caps.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
        caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        caps.setCapability("pageLoadStrategy", "none");
        caps.setCapability("enablePersistentHover", true);
        return caps;
    }

    public DesiredCapabilities getLocalCapabilities() {
        DesiredCapabilities caps;
        try {
            switch (Config.getBrowserName()) {
                case IE11:
                    caps = getLocalIECapabilities();
                    break;
                case Chrome:
                    caps = getLocalChromeCapabilities();
                    break;
                default:
                    caps = getLocalChromeCapabilities();
                    break;
                //case ChromeLinux:
                //  caps = getLocalChromeCapabilities();
                //break;
                case HeadLess:
                    caps = getLocalHeadLessCapabilities();
                    break;
                //case HeadLessLinux:
                //	caps = getLocalHeadLessCapabilities();
                //	break;
            }
            return caps;
        } catch (Exception e) {
            TestLogger.debug("UnSupported browser", e);
            return null;
        }
    }

    public DesiredCapabilities getGridCapabilities() {
        DesiredCapabilities caps;
        try {
            switch (Config.getBrowserName()) {
                case IE11:
                    caps = getGridIECapabilities();
                    break;
                case Chrome:
                default:
                    caps = getGridChromeCapabilities();
                    break;
                //case ChromeLinux:
                //caps = getGridChromeCapabilities();
                //break;
                case HeadLess:
                    caps = getGridHeadLessCapabilities();
                    break;
                //case HeadLessLinux:
                //caps = getGridHeadLessCapabilities();
                //break;
            }
            return caps;
        } catch (Exception e) {
            TestLogger.debug("Unsupported Remote browser", e);
            return null;
        }
    }

}
