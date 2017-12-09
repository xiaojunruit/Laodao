package com.ejz.update;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by WORK on 2017/3/20.
 */

public class IoUtil {
    public IoUtil() {
    }

    public static void close(Closeable closeable) {
        if(closeable != null) {
            try {
                closeable.close();
            } catch (IOException var2) {
                var2.printStackTrace();
            }
        }

    }

    public static long copy(File in, OutputStream out) throws IOException {
        return copy((InputStream)(new FileInputStream(in)), (OutputStream)out);
    }

    public static long copy(InputStream in, File out) throws IOException {
        return copy((InputStream)in, (OutputStream)(new FileOutputStream(out)));
    }

    public static long copy(InputStream input, OutputStream output) throws IOException {
        long var2;
        try {
            var2 = copy0(input, output);
        } finally {
            close(input);
            close(output);
        }

        return var2;
    }

    public static long copy0(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[4096];

        long count;
        int n;
        for(count = 0L; -1 != (n = input.read(buffer)); count += (long)n) {
            output.write(buffer, 0, n);
        }

        output.flush();
        return count;
    }

    public static String readString(File in) throws IOException {
        return readString((InputStream)(new FileInputStream(in)));
    }

    public static String readString(InputStream is) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        copy((InputStream)is, (OutputStream)os);
        return new String(os.toByteArray(), "UTF-8");
    }

    public static void writeString(String data, File out) throws IOException {
        copy((InputStream)(new ByteArrayInputStream(data.getBytes())), (OutputStream)(new FileOutputStream(out)));
    }

    public static void writeString(String data, OutputStream os) throws IOException {
        copy((InputStream)(new ByteArrayInputStream(data.getBytes())), (OutputStream)os);
    }
}
