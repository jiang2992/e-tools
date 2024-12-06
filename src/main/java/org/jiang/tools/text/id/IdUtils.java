package org.jiang.tools.text.id;

import java.util.UUID;
import org.jiang.tools.exception.BadArgumentException;
import org.jiang.tools.lock.SpinLock;

/**
 * ID工具类
 *
 * @author Bin
 * @since 1.0.0
 */
public class IdUtils {

    /**
     * 生成UUID
     * 生成一串长度为32的随机字符串
     *
     * @return UUID
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
