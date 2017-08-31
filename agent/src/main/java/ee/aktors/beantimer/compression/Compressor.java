package ee.aktors.beantimer.compression;

import java.io.*;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

/**
 *
 */
public class Compressor {

    public static void decompress(byte[] data) throws Exception {
        InputStream in = new ByteArrayInputStream(data);
        InflaterInputStream ini = new InflaterInputStream(in);
        ByteArrayOutputStream bout = new ByteArrayOutputStream(512);
        int b;
        while ((b = ini.read()) != -1) {
            bout.write(b);
        }
        ini.close();
        bout.close();
        String s = new String(bout.toByteArray(), "UTF-8");
        System.out.print(s);
    }

    public static byte[] compressData(byte[] data) throws Exception {

        Deflater d = new Deflater(1);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DeflaterOutputStream dos = new DeflaterOutputStream(baos, d);
        dos.write(data);
        dos.flush();
        dos.close();

        return baos.toByteArray();
    }


    public static void main(String[] args) throws Exception {
        byte[] compressed = compressData("My name is Motasem".getBytes());
        decompress(compressed);

    }

}
