package ee.aktors.beantimer.util;

import ee.aktors.beantimer.model.Measurement;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class JsonUtilTest {

    @Test
    public void testTansform() throws Exception {

        assertEquals("[]", JsonUtil.transformToJsonArray(Arrays.asList()));
        assertEquals("[{\"beanName\":\"a\",\"beanType\":\"b\",\"duration\":\"1\"}]", JsonUtil.transformToJsonArray(Arrays.asList()));
        assertEquals("[{\"beanName\":\"a\",\"beanType\":\"b\",\"duration\":\"1\"},{\"beanName\":\"a\",\"beanType\":\"b\",\"duration\":\"2\"}]", JsonUtil.transformToJsonArray(Arrays.asList(new Measurement("a", "b", 1L, 10L), new Measurement("a", "b", 2L, 20L))));

    }

}