package core;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.apache.commons.configuration2.ex.ConfigurationException;

/**
 * @author Rajaraman Mahalingam
 * @version 1.0.1
 */

public abstract class BaseTestCase {

    @Rule
    public final TestName testName = new TestName();
    @Rule
    public TestWatcher watchman = new TestWatcher() {

        @Override
        public void failed(Throwable e, Description description) {
            TestLogger.setResult(ExecutionStatus.FAILED, e);
            TestLogger.info("Execution Status:" + ExecutionStatus.FAILED.name(), e);
            Utilities.takeScreenShot();
        }

        @Override
        public void succeeded(Description description) {
            TestLogger.setResult(ExecutionStatus.PASSED);
            TestLogger.info("Execution Status:" + ExecutionStatus.PASSED.name());
        }

        @Override
        public void skipped(org.junit.internal.AssumptionViolatedException e, Description description) {
            TestLogger.setResult(ExecutionStatus.SKIPPED, e);
            TestLogger.info("Execution Status:" + ExecutionStatus.SKIPPED.name(),e);
        }

        @Override
        public void starting(Description description) {
            TestLogger.setResult(ExecutionStatus.STARTING);
        }

        @Override
        public void finished(Description description) {
            TestLogger.setResult(ExecutionStatus.FINISHED);
        }
    };

    @BeforeClass
    public static void classInit() throws ConfigurationException {

        /*
          Check if the site is up and Running using http methods if 200, then
          => Initialize the Test Logger && Open the B Instance if B Instance
          fails => Catch Exception, Fail the test and quit! else, then => Fail
          the test and quit!
         */
        TestLogger.init();
        TestLogger.info("Running " + Config.getRunCondition() + "tests on " + Config.getEnvironment());
        try {
            Browser.openBrowser();
        } catch (Exception e) {
            TestLogger.fatal("=>Setup Error!", e);
            TestLogger.quit();
        }
    }

    @AfterClass
    public static void classQuit() throws ConfigurationException {
        /*
          Close the Browser Instance and quit. Catch any Exception in the
          process.
         */
        TestLogger.info("Finished " + Config.getRunCondition() + "tests of " + Config.getTestClass()+ " on " + Config.getEnvironment());
        TestLogger.quit();
        try {
            Browser.close();
        }catch (Exception e) {
            TestLogger.fatal("Unable to close instance!", e);
        }
    }

    @Before
    public void testSetup() throws ClassNotFoundException, NoSuchMethodException, ConfigurationException {

        /*
          Check if the testRunCondition of this test is matching the global
          Test Run condition set in the automation.properties file. If not
          matching, then Skip the test! If Matching, then proceed with the
          test.
         */
        Config.setTestName(testName.getMethodName());
        Config.setTestClass(this.getClass().getName());
        Config.getTestDescription();
        Assume.assumeTrue("runcondition", Config.verifyRunCondition());
        Assume.assumeTrue("priority", Config.verifyPriority());
        TestLogger.doTestStart();
    }

    @After
    public void testTearDown() {
        /*
          Close the Browser instance, clear Browser session for this test. And
          continue test!
         */
        TestLogger.doTestEnd();
    }
}
