package com.example.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {
    private BeanCopyUtils() {

    }

    public static <V> V copyBean(Object source, Class<V> clazz) {
        V result;
        try {
            result = clazz.newInstance();

            BeanUtils.copyProperties(source, result);

        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static <O,V> List<V> copyBeanList(List<O> sourceList, Class<V> clazz) {
        return sourceList.stream()
                .map(o->copyBean(o,clazz))
                .collect(Collectors.toList());
    }
}
