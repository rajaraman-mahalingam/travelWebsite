package core;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class Automation extends BaseTestCase {

    @BeforeClass
    public static void classInit() throws ConfigurationException {
        BaseTestCase.classInit();
    }

    @AfterClass
    public static void classQuit() throws ConfigurationException {
        BaseTestCase.classQuit();
    }
}
