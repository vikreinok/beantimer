package ee.aktors.beantimer.util;

import java.util.List;

/**
 *
 */
public class JsonUtil {

    /**
     * Object.toString() has to return json
     */
    public static String transformToJsonArray(List<? extends Object> objectsWithJsonToString) {

        StringBuilder json = new StringBuilder("[");
        for (Object object : objectsWithJsonToString) {
            json.append(object.toString()).append(",");
        }

        if (objectsWithJsonToString.size() > 0 && json.charAt(json.length() - 1) == ',') {
            json = new StringBuilder(json.substring(0, json.length() - 1));
        }

        json.append("]");
        return json.toString();
    }
}
