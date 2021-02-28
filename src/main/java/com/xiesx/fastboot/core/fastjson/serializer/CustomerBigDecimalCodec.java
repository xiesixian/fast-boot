package com.xiesx.fastboot.core.fastjson.serializer;

import com.alibaba.fastjson.serializer.*;

import cn.hutool.core.util.NumberUtil;

public class CustomerBigDecimalCodec extends BigDecimalCodec implements ContextObjectSerializer {

    /**
     * 当BigDecimal类型的属性上有@JSONFiled注解，且该注解中的format有值时，使用该方法进行序列化，否则使用fastjson的
     * BigDecimalCodec中的write方法进行序列化
     */
    @Override
    public void write(JSONSerializer serializer, Object object, BeanContext context) {
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeString("");
            return;
        }
        out.writeString(NumberUtil.decimalFormat(context.getFormat(),object));
    }
}
