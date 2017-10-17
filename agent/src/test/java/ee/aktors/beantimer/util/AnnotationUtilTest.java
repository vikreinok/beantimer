package ee.aktors.beantimer.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class AnnotationUtilTest {

    @Test
    public void parseFirstArgumentValue() throws Exception {

        assertEquals("session", AnnotationUtil.parseFirstArgumentValue("@org.springframework.context.annotation.Scope(value=\"session\", proxyMode=org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS)"));
        assertEquals("prototype", AnnotationUtil.parseFirstArgumentValue("@org.springframework.context.annotation.Scope(value=\"prototype\")"));
        assertEquals("bean name", AnnotationUtil.parseFirstArgumentValue("@org.springframework.beans.factory.annotation.Qualifier(value=\"bean name\")"));
        assertEquals("bean name", AnnotationUtil.parseFirstArgumentValue("@org.springframework.beans.factory.annotation.Bean(name=\"bean name\")"));
        assertEquals("bean name", AnnotationUtil.parseFirstArgumentValue("@javax.inject.Named(name=\"bean name\")"));

    }

}