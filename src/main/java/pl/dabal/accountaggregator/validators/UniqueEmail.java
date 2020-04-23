package pl.dabal.accountaggregator.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Constraint(validatedBy = UniqueEmailValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
    String message() default "email must be unique";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {}; }

