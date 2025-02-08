package org.jiang.tools.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/**
 * 集合工具类
 *
 * @author Bin
 * @since 1.1.3
 */
public class CollectionUtils {

    /**
     * 返回集合是否为空
     *
     * @param collection 集合
     * @return 是否为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 返回集合是否非空
     *
     * @param collection 集合
     * @return 是否非空
     */
    public static boolean notEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 获取元素集合中的第一个元素
     *
     * @param collection 集合
     * @param <T>        元素类型
     * @return 元素
     */
    public static <T> T getFirstElement(Collection<T> collection) {
        if (isEmpty(collection)) {
            return null;
        }

        if (collection instanceof List) {
            List<T> list = (List<T>) collection;
            return list.get(0);
        }

        return collection.iterator().next();
    }

    /**
     * 获取元素集合中的最后一个元素
     *
     * @param collection 集合
     * @param <T>        元素类型
     * @return 元素
     */
    public static <T> T getLastElement(Collection<T> collection) {
        if (isEmpty(collection)) {
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

    /**
     * 获取元素集合中指定下标的元素
     *
     * @param collection 集合
     * @param index      下标
     * @param <T>        元素类型
     * @return 元素
     */
    public static <T> T getElement(Collection<T> collection, int index) {
        if (isEmpty(collection)) {
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

    /**
     * 将集合元素分批
     *
     * @param collection 集合
     * @param size       每一批的元素个数
     * @param consumer   消费者
     * @param <T>        元素类型
     */
    public static <T> void batch(Collection<T> collection, int size, Consumer<Collection<T>> consumer) {
        if (isEmpty(collection)) {
            return;
        }

        Collection<T> subList = null;
        Iterator<T> iterator = collection.iterator();
        int currentIndex = 0;
        while (iterator.hasNext()) {
            T element = iterator.next();
            if (subList == null) {
                subList = new ArrayList<>(size);
            }
            subList.add(element);
            if (++currentIndex % size == 0) {
                consumer.accept(subList);
                subList = null;
            }
        }

        if (subList != null) {
            consumer.accept(subList);
        }
    }

    /**
     * 将集合元素分批
     *
     * @param collection 集合
     * @param size       每一批的元素个数
     * @param <T>        元素类型
     * @return 分批集合
     */
    public static <T> Collection<Collection<T>> batch(Collection<T> collection, int size) {
        if (isEmpty(collection)) {
            return Collections.emptyList();
        }
        Collection<Collection<T>> result = new ArrayList<>();
        batch(collection, size, result::add);
        return result;
    }

}
