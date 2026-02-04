package org.jiang.tools.data;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;
import org.jiang.tools.net.NetResourceUtils;

/**
 * EasyData：提供小数据的存储和基础操作
 *
 * @author Bin
 * @since 1.1.3
 */
public class EasyData {

    private byte[] bytes;

    private Supplier<byte[]> supplier;

    private boolean executed = false;

    public EasyData(byte[] bytes) {
        this.supplier = null;
        this.bytes = bytes;
    }

    public EasyData(Supplier<byte[]> supplier) {
        this.bytes = null;
        this.supplier = supplier;
    }

    public static EasyData of(byte[] bytes) {
        return new EasyData(bytes);
    }

    public static EasyData of(String string) {
        return new EasyData(() -> string.getBytes(StandardCharsets.UTF_8));
    }

    public static EasyData of(URL url) {
        return new EasyData(() -> {
            try {
                return NetResourceUtils.getBytes(url);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public EasyData reset() {
        if (supplier != null) {
            this.bytes = null;
        }
        this.executed = false;
        return this;
    }

    public void destroy() {
        this.bytes = null;
        this.supplier = null;
    }

    public byte[] value() {
        if (bytes != null) {
            return bytes;
        }
        if (executed || supplier == null) {
            return null;
        }
        this.executed = true;
        this.bytes = supplier.get();
        return this.bytes;
    }

    public String stringValue() {
        return new String(this.value(), StandardCharsets.UTF_8);
    }

    public InputStream streamValue() {
        return new ByteArrayInputStream(this.value());
    }

    public void write(OutputStream outputStream) throws IOException {
        outputStream.write(this.value());
    }

}
