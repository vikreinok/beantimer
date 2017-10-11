package ee.aktors.beantimer.util;

/**
 *
 */
public class AnnotationUtil {

    public static String parseValue(String proxy) {
        int beginIndex = proxy.indexOf("\"") + 1;
        return proxy.substring(beginIndex, proxy.indexOf("\"", beginIndex));
    }
}
