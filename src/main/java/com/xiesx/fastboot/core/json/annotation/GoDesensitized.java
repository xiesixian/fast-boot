package com.xiesx.fastboot.core.json.annotation;

import java.lang.annotation.*;

import com.xiesx.fastboot.core.json.desensitized.SensitiveTypeEnum;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface GoDesensitized {
    
    // 脱敏类型(规则)
    SensitiveTypeEnum type();
}