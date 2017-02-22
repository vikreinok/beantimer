package ee.aktors.beantimer;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class ClassFilterTest {

    ClassFilter classFilter;

    @Before
    public void setUp() throws Exception {
        classFilter = new ClassFilter();
    }

    @Test
    public void matches() throws Exception {
        assertEquals(true, classFilter.matches("com.company.product.file"));
        assertEquals(false, classFilter.matches("ee.company.product.file"));
    }

}