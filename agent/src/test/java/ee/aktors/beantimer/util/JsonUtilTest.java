package ee.aktors.beantimer.util;

import ee.aktors.beantimer.model.Measurement;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class JsonUtilTest {

    @Test
    public void testTransform() throws Exception {

        assertEquals("[]", JsonUtil.transformToJsonArray(Collections.emptyList()));
        assertEquals("[{\"beanName\":\"a\",\"beanType\":\"b\",\"beanScope\":\"singleton\",\"duration\":\"1\",\"initialisationStartTimeMillis\":\"10\",\"primaryBean\":false },{\"beanName\":\"a\",\"beanType\":\"b\",\"beanScope\":\"singleton\",\"duration\":\"2\",\"initialisationStartTimeMillis\":\"20\",\"primaryBean\":true }]", JsonUtil.transformToJsonArray(Arrays.asList(new Measurement("a", "b", "singleton", 1L, 10L, false), new Measurement("a", "b", "singleton", 2L, 20L, true))));

    }

}