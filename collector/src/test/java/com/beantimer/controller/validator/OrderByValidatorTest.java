package com.beantimer.controller.validator;

import com.beantimer.model.ProcessedMetric;
import org.junit.Test;

/**
 *
 */
public class OrderByValidatorTest {


    @Test
    public void validate_allCorrect_doesNotThrowException() throws Exception {
        OrderByValidator.validate(ProcessedMetric.class, "beanName", "ASC");
    }

    @Test(expected = IllegalArgumentException.class)
    public void validate_invalidDirection_throwException() throws Exception {
        OrderByValidator.validate(ProcessedMetric.class, "beanName", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void validate_invalidColumnName_throwException() throws Exception {
        OrderByValidator.validate(ProcessedMetric.class, "beanName", "");
    }

}