package com.asiainfo.ai.common.annotction;

import com.asiainfo.ai.common.annotction.validator.PhoneNumberValidator;

import javax.validation.Constraint;
import java.lang.annotation.*;

/**
 * @author zhangls
 */
@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumber {

	String message() default "手机号无效";

	Class[] groups() default {};

	Class[] payload() default {};

}
