package ee.aktors.beantimer.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class AnnotationUtilTest {

    @Test
    public void parseValue() throws Exception {

        String proxy = "@org.springframework.context.annotation.Scope(value=\"session\", proxyMode=org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS)";
        assertEquals("session", AnnotationUtil.parseValue(proxy));

    }

}