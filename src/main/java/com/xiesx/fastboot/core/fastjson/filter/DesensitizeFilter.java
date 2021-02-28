package com.xiesx.fastboot.core.fastjson.filter;

import java.lang.reflect.Field;

import com.alibaba.fastjson.serializer.ValueFilter;
import com.xiesx.fastboot.core.fastjson.annotation.GoDesensitized;
import com.xiesx.fastboot.core.fastjson.desensitized.SensitiveTypeEnum;
import com.xiesx.fastboot.core.fastjson.utils.DesensitizedUtils;

/**
 * 在fastjson中使用此过滤器进行脱敏操作
 */
public class DesensitizeFilter implements ValueFilter {

    @Override
    public Object process(Object object, String name, Object value) {
        if (null == value || !(value instanceof String) || ((String) value).length() == 0) {
            return value;
        }
        try {
            Field field = object.getClass().getDeclaredField(name);
            GoDesensitized desensitization;
            if (String.class != field.getType() || (desensitization = field.getAnnotation(GoDesensitized.class)) == null) {
                return value;
            }
            String valueStr = (String) value;
            SensitiveTypeEnum type = desensitization.type();
            switch (type) {
                case CHINESE_NAME:
                    return DesensitizedUtils.chineseName(valueStr);
                case ID_CARD:
                    return DesensitizedUtils.idCardNum(valueStr);
                case PHONE:
                    return DesensitizedUtils.fixedPhone(valueStr);
                case MOBILE:
                    return DesensitizedUtils.mobilePhone(valueStr);
                case ADDRESS:
                    return DesensitizedUtils.address(valueStr, 8);
                case EMAIL:
                    return DesensitizedUtils.email(valueStr);
                case BANK_CARD:
                    return DesensitizedUtils.bankCard(valueStr);
                case PASSWORD:
                    return DesensitizedUtils.password(valueStr);
                case CARNUMBER:
                    return DesensitizedUtils.carNumber(valueStr);
                default:
            }
        } catch (NoSuchFieldException e) {
            return value;
        }
        return value;
    }
}

