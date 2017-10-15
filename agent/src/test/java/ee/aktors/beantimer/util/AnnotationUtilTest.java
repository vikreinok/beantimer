package ee.aktors.beantimer.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class AnnotationUtilTest {

    @Test
    public void parseValue() throws Exception {

        assertEquals("session", AnnotationUtil.parseValue("@org.springframework.context.annotation.Scope(value=\"session\", proxyMode=org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS)"));
        assertEquals("prototype", AnnotationUtil.parseValue("@org.springframework.context.annotation.Scope(value=\"prototype\")"));
        assertEquals("abc", AnnotationUtil.parseValue("@org.springframework.beans.factory.annotation.Qualifier(value=\"abc\")"));

    }

}