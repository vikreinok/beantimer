package ee.aktors.beantimer.compression;

import org.junit.Test;

import static ee.aktors.beantimer.compression.Compressor.compressData;
import static ee.aktors.beantimer.compression.Compressor.decompress;
import static org.junit.Assert.assertEquals;

/**
 *
 */
public class CompressorTest {

    @Test
    public void testCompressor() throws Exception {
        String inputData = "Test string";

        String outputData = decompress(compressData(inputData.getBytes("UTF-8")));

        assertEquals(inputData, outputData);
    }
}