package org.jiang.tools.data;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * EasyData：提供小数据的存储和基础操作
 *
 * @author Bin
 * @since 1.1.3
 */
public class EasyData {

    private final byte[] bytes;
    private String string;

    public EasyData(byte[] bytes) {
        this.bytes = bytes;
    }

    public static EasyData of(byte[] bytes) {
        return new EasyData(bytes);
    }

    public static EasyData of(String string) {
        EasyData easyData = of(string.getBytes());
        easyData.string = string;
        return easyData;
    }

    public byte[] value() {
        return this.bytes;
    }

    public String stringValue() {
        if (this.string == null) {
            this.string = new String(this.bytes, StandardCharsets.UTF_8);
        }
        return this.string;
    }

    public InputStream streamValue() {
        return new ByteArrayInputStream(bytes);
    }

    public void write(OutputStream outputStream) throws IOException {
        outputStream.write(bytes);
    }

}
