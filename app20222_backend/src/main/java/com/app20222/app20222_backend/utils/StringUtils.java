package com.app20222.app20222_backend.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class StringUtils {

    /**
     * Covert string long array '{0,1,2,...}' to Set<Long> in Java
     */
    public static Set<Long> convertStrLongArrayToSetLong(String longArray) {
        if (longArray == null) {
            new HashSet<Long>();
        }
        return Arrays.
            stream(longArray.replace("{", "").replace("}", "").split(","))
            .map(Long::valueOf)
            .collect(Collectors.toSet());
    }

}
