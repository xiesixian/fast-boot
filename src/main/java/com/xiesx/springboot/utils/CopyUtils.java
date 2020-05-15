package com.xiesx.springboot.utils;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import lombok.NonNull;

public class CopyUtils {

    public static String[] ignoreNullNames(@NonNull Object source) {

        final BeanWrapper beanWrapper = new BeanWrapperImpl(source);

        PropertyDescriptor[] pds = beanWrapper.getPropertyDescriptors();

        Set<String> noValuePropertySet = new HashSet<>();
        Arrays.stream(pds).forEach(pd -> {
            Object propertyValue = beanWrapper.getPropertyValue(pd.getName());
            if (ObjectUtils.isEmpty(propertyValue)) {
                noValuePropertySet.add(pd.getName());
            } else {
                if (Iterable.class.isAssignableFrom(propertyValue.getClass())) {
                    Iterable<?> iterable = (Iterable<?>) propertyValue;
                    Iterator<?> iterator = iterable.iterator();
                    if (!iterator.hasNext())
                        noValuePropertySet.add(pd.getName());
                }
                if (Map.class.isAssignableFrom(propertyValue.getClass())) {
                    Map<?, ?> map = (Map<?, ?>) propertyValue;
                    if (map.isEmpty())
                        noValuePropertySet.add(pd.getName());
                }
            }
        });
        String[] result = new String[noValuePropertySet.size()];
        return noValuePropertySet.toArray(result);
    }
}
