package org.jiang.tools.secutiry;

import java.nio.charset.StandardCharsets;
import org.jiang.tools.text.StringUtils;

/**
 * MurmurHash3 算法工具类
 *
 * @author Bin
 * @since 2025/3/26 14:26
 */
public class MurmurHash3 {

    /**
     * 常数参数
     */
    private static final int C1 = 0xcc9e2d51;
    private static final int C2 = 0x1b873593;
    private static final int R1 = 15;
    private static final int R2 = 13;
    private static final int M = 5;
    private static final int N = 0xe6546b64;

    /**
     * 种子
     */
    private static final int SEED = 0;

    public static int hash32(Object data) {
        if (data == null) {
            return 0;
        }
        return hash32(data.toString().getBytes(StandardCharsets.UTF_8));
    }

    public static int hash32(String data) {
        if (StringUtils.isEmpty(data)) {
            return 0;
        }
        return hash32(data.getBytes(StandardCharsets.UTF_8));
    }


    public static int hash32(byte[] data) {
        int length = data.length;
        int hash = SEED;
        int blocks = length / 4;

        // 处理数据块（4字节为一块）
        for (int i = 0; i < blocks; i++) {
            int k = (data[i * 4] & 0xFF)
                    | ((data[i * 4 + 1] & 0xFF) << 8)
                    | ((data[i * 4 + 2] & 0xFF) << 16)
                    | ((data[i * 4 + 3] & 0xFF) << 24);

            k *= C1;
            k = Integer.rotateLeft(k, R1);
            k *= C2;

            hash ^= k;
            hash = Integer.rotateLeft(hash, R2);
            hash = hash * M + N;
        }

        // 处理剩余的数据（不足4字节的部分）
        int remaining = length % 4;
        if (remaining != 0) {
            int k = 0;
            for (int i = 0; i < remaining; i++) {
                k |= (data[blocks * 4 + i] & 0xFF) << (i * 8);
            }

            k *= C1;
            k = Integer.rotateLeft(k, R1);
            k *= C2;

            hash ^= k;
        }

        // 最后处理混合
        hash ^= length;
        hash = mixing(hash);

        return hash;
    }

    /**
     * 最终混合函数
     *
     * @param hash hash
     * @return result
     */
    private static int mixing(int hash) {
        hash ^= (hash >>> 16);
        hash *= 0x85ebca6b;
        hash ^= (hash >>> 13);
        hash *= 0xc2b2ae35;
        hash ^= (hash >>> 16);
        return hash;
    }

}
