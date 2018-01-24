package core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Rajaraman Mahalingam
 * @version 1.0.1
 */

public class TestRunManager {
    private static <T extends Annotation> T getTestAnnotation(Class<T> annotationClass)
            throws ClassNotFoundException, NoSuchMethodException, SecurityException {
        Class<?> clazz;
        Method method;
        try {
            clazz = (Config.getTestClass() == null) ? null : Class.forName(Config.getTestClass());
            method = (Config.getTestName() == null) ? null : clazz != null ? clazz.getMethod(Config.getTestName()) : null;
        } catch (Exception e) {
            return null;
        }
        if ((method == null) || !method.isAnnotationPresent(annotationClass)) {
            return null;
        }
        return method.getAnnotation(annotationClass);
    }

    static String getTestDescription() throws ClassNotFoundException, NoSuchMethodException {
        try {
            if (getTestAnnotation(TestDescription.class) == null) {
                return "";
            }
            TestDescription testDescription = getTestAnnotation(TestDescription.class);
            return testDescription != null ? testDescription.value() : null;
        } catch (NullPointerException e) {
            return "";
        }
    }

    static String getTestRunConditon() throws ClassNotFoundException, NoSuchMethodException {
        try {
            if (getTestAnnotation(RunCondition.class) == null) {
                return "";
            }
            RunCondition runCondition = getTestAnnotation(RunCondition.class);
            return runCondition != null ? runCondition.value() : null;
        } catch (NullPointerException e) {
            return "";
        }
    }

    static String getTestPriority() throws ClassNotFoundException, NoSuchMethodException {
        try {
            if (getTestAnnotation(Priority.class) == null) {
                return "";
            }
            Priority priority = getTestAnnotation(Priority.class);
            return priority != null ? priority.value() : null;
        } catch (NullPointerException e) {
            return "";
        }
    }
}
