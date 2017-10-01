package com.beantimer.controller.validator;

import com.beantimer.model.ProcessedMetric;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class OrderByValidatorTest {


    @Test
    public void checkIfClazzContainsColumn_classContainsFieldName_true() throws Exception {
        assertEquals(true, OrderByValidator.checkIfClazzContainsColumn(ProcessedMetric.class, "beanName"));
    }

    @Test
    public void checkIfClazzContainsColumn_classDoesNotContainFieldName_false() throws Exception {
        assertEquals(false, OrderByValidator.checkIfClazzContainsColumn(ProcessedMetric.class, "beanNameX"));
    }

    @Test
    public void checkIfClazzContainsColumn_validValues_true() throws Exception {
        assertEquals(true, OrderByValidator.validateDirection("asc"));
        assertEquals(true, OrderByValidator.validateDirection("ASC"));
        assertEquals(true, OrderByValidator.validateDirection("desc"));
        assertEquals(true, OrderByValidator.validateDirection("DESC"));
    }

    @Test
    public void checkIfClazzContainsColumn_invalidValues_false() throws Exception {
        assertEquals(false, OrderByValidator.validateDirection("DESCx"));
    }

}