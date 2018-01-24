package core;

/**
 * @author Rajaraman Mahalingam
 * @version 1.0.1
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)

public @interface RunCondition {
    String value() default "";
}
