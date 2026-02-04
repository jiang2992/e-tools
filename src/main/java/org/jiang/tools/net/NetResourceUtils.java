package org.jiang.tools.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * 网络资源工具类
 *
 * @author Bin
 * @since 1.1.8
 */
public class NetResourceUtils {

    public static byte[] getBytes(String url) throws IOException {
        return getBytes(new URL(url));
    }

    public static byte[] getBytes(URL url) throws IOException {
        try (InputStream inputStream = url.openStream();
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            return byteArrayOutputStream.toByteArray();
        }
    }

}
