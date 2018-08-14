package com.demo.other;


import java.lang.annotation.*;

@Target({ElementType.FIELD,})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnumAnnoation {
    String value();
}
