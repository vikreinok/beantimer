package ee.aktors.beantimer.util;

/**
 *
 */
public class AnnotationUtil {

    public static String parseFirstArgumentValue(String proxy) {
        int beginIndex = proxy.indexOf("\"") + 1;
        return proxy.substring(beginIndex, proxy.indexOf("\"", beginIndex));
    }
}
