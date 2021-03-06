package ee.aktors.beantimer.compression;

import java.io.*;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

/**
 *
 */
public class Compressor {

    public static final int BUFFER_SIZE = 512;
    public static final int COMPRESSION_LEVEL = 1;

    public static String decompress(byte[] data) throws Exception {
        InputStream in = new ByteArrayInputStream(data);
        InflaterInputStream ini = new InflaterInputStream(in);
        ByteArrayOutputStream bout = new ByteArrayOutputStream(BUFFER_SIZE);
        int b;
        while ((b = ini.read()) != -1) {
            bout.write(b);
        }
        ini.close();
        bout.close();
        return new String(bout.toByteArray(), "UTF-8");
    }

    public static byte[] compressData(byte[] data) throws Exception {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(byteArrayOutputStream, new Deflater(COMPRESSION_LEVEL));
        deflaterOutputStream.write(data);
        deflaterOutputStream.flush();
        deflaterOutputStream.close();

        return byteArrayOutputStream.toByteArray();
    }

}
