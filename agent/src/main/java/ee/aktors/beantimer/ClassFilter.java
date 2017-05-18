package ee.aktors.beantimer;

import java.util.regex.Pattern;

/**
 *
 */
public class ClassFilter {


    Pattern pattern;

    public ClassFilter(String packageToMeasure) {
        pattern = Pattern.compile(packageToMeasure + ".[\\w.-]*");
    }

    public boolean matches(String str) {

        if (str == null) {
            return false;
        }

        str = transformClasspath(str);

        return pattern.matcher(str).matches();
    }

    public String transformClasspath(String s) {
        return s.replaceAll("/", ".");
    }
}
