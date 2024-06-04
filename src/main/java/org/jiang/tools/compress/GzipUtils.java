package org.jiang.tools.compress;


import org.jiang.tools.data.EasyData;
import org.jiang.tools.text.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Gzip工具类
 *
 * @author Bin
 * @since 1.1.3
 */
public class GzipUtils {

    /**
     * 压缩字节数组
     *
     * @param bytes 字节数组
     * @return EasyData
     * @throws IOException 压缩过程中的异常
     */
    public static EasyData compress(byte[] bytes) throws IOException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
            gzipOutputStream.write(bytes);
        }
        return EasyData.of(byteArrayOutputStream.toByteArray());
    }

    /**
     * 压缩字符串
     *
     * @param str 字符串
     * @return EasyData
     * @throws IOException 压缩过程中的异常
     */
    public static EasyData compress(String str) throws IOException {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        return compress(str.getBytes());
    }

    /**
     * 压缩数据
     *
     * @param data 数据
     * @return EasyData
     * @throws IOException 压缩过程中的异常
     */
    public static EasyData compress(EasyData data) throws IOException {
        if (data == null) {
            return null;
        }
        return compress(data.value());
    }

    /**
     * 解压缩字节数组
     *
     * @param bytes 字节数组
     * @return EasyData
     * @throws IOException 解压缩过程中的异常
     */
    public static EasyData decompress(byte[] bytes) throws IOException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream)) {
            byte[] buffer = new byte[8192];
            int len;
            while ((len = gzipInputStream.read(buffer)) > 0) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
        }
        return EasyData.of(byteArrayOutputStream.toByteArray());
    }

    /**
     * 解压缩字符串
     *
     * @param str 字符串
     * @return EasyData
     * @throws IOException 解压缩过程中的异常
     */
    public static EasyData decompress(String str) throws IOException {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        return decompress(str.getBytes());
    }

    /**
     * 解压缩数据
     *
     * @param data 数据
     * @return EasyData
     * @throws IOException 解压缩过程中的异常
     */
    public static EasyData decompress(EasyData data) throws IOException {
        if (data == null) {
            return null;
        }
        return decompress(data.value());
    }

}
