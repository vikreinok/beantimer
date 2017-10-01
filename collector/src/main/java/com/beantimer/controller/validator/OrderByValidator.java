package com.beantimer.controller.validator;

import java.lang.reflect.Field;

/**
 *
 */
public class OrderByValidator {


    public static boolean checkIfClazzContainsColumn(Class clazz, String columnName) {
        boolean containsField = false;
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().equalsIgnoreCase(columnName)) {
                containsField = true;
                break;
            }
        }
        return containsField;
    }

    public static boolean validateDirection(String direction) {
        return "ASC".equalsIgnoreCase(direction) || "DESC".equalsIgnoreCase(direction);
    }
}
