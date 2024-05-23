package org.jiang.tools.collection;

import java.util.Collection;
import java.util.Collections;

/**
 * 集合工具类
 *
 * @author Bin
 * @since 1.1.3
 */
public class CollectionUtils {

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean notEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

}
