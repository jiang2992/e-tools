package org.jiang.tools.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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

    public static <T> T getFirstElement(Collection<T> collection) {
        if (collection == null || collection.isEmpty()) {
            return null;
        }

        if (collection instanceof List) {
            List<T> list = (List<T>) collection;
            return list.get(0);
        }

        return collection.iterator().next();
    }

    public static <T> T getLastElement(Collection<T> collection) {
        if (collection == null || collection.isEmpty()) {
            return null;
        }

        if (collection instanceof List) {
            List<T> list = (List<T>) collection;
            return list.get(list.size() - 1);
        }

        Iterator<T> iterator = collection.iterator();
        T lastElement = null;
        while (iterator.hasNext()) {
            lastElement = iterator.next();
        }

        return lastElement;
    }

    public static <T> T getElement(Collection<T> collection, int index) {
        if (collection == null || collection.isEmpty()) {
            return null;
        }

        if (collection instanceof List) {
            List<T> list = (List<T>) collection;
            return list.get(index);
        }

        Iterator<T> iterator = collection.iterator();
        int currentIndex = 0;
        while (iterator.hasNext()) {
            T element = iterator.next();
            if (currentIndex == index) {
                return element;
            }
            currentIndex++;
        }

        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + collection.size());
    }


}
