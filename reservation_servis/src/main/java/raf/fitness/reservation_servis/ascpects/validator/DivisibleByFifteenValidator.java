package raf.fitness.reservation_servis.ascpects.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DivisibleByFifteenValidator implements ConstraintValidator<DivisibleByFifteen, Integer> {

    @Override
    public void initialize(DivisibleByFifteen constraintAnnotation) { }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value == null || value % 15 == 0;
    }
}

