package com.maroc_ouvrage.semployee.audit;


import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auditable {
    String action();   // short label, e.g., "EMPLOYEE_ADDED"
    String details() default ""; // extra info
}

