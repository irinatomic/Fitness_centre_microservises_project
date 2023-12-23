package raf.fitness.reservation_servis.ascpects.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DivisibleByFifteenValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface DivisibleByFifteen {

    String message() default "Value must be divisible by 15";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
