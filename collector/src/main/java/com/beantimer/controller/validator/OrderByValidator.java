package com.beantimer.controller.validator;

import java.lang.reflect.Field;

/**
 *
 */
public class OrderByValidator {


    public static void validate(Class clazz, String columnName, String direction) {

        boolean validDirection = false;
        if ("ASC".equalsIgnoreCase(direction) || "DESC".equalsIgnoreCase(direction)) {
            validDirection = true;
        }

        boolean containsField = false;
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().equalsIgnoreCase(columnName)) {
                containsField = true;
                continue;
            }
        }

        if (!containsField || !validDirection) {
            throw new IllegalArgumentException(String.format("Class %s contains %s  dir in {'ASC', 'DESC'} %s", clazz.getName(), columnName, direction));
        }

    }
}
