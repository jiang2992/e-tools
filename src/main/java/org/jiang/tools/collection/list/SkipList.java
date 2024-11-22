package org.jiang.tools.collection.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 跳表
 * <br> 特点：自然排序、分数可重复、元素不可重复
 *
 * @author Bin
 * @since 1.1.5
 */
public class SkipList<E extends Comparable<E>> implements Collection<E>, Cloneable, java.io.Serializable {

    public static final byte MAX_INDEX_LEVEL = 32;

    private int p = 50;

    private int size = 0;

    private int indexLevel = 0;

    private Node<E> header;

    private Node<E> first;

    private Node<E> last;

    public SkipList() {
        header = new Node<>(null, null, null);
    }

    /**
     * 指定生成每一层的概率，取值 1 - 99
     * <br> 默认值为50
     *
     * @param p 概率
     */
    public SkipList(int p) {
        this();
        this.p = p;
    }

    public SkipList(Collection<E> collection) {
        this();
        this.addAll(collection);
    }

    /**
     * 返回跳表的大小
     *
     * @return 大小
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * 判断跳表是否是空的
     *
     * @return 是否空的
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 判断元素是否在跳表中
     * <br> 该方法最终会调用元素的equals方法进行对比，来判断两个元素是否是完全相同的
     *
     * @param o 元素
     * @return 是否存在
     */
    @Override
    public boolean contains(Object o) {
        if (!(o instanceof Comparable) || isEmpty()) {
            return false;
        }
        E element = (E) o;
        Node<E>[] path = getPath(element);
        Node<E> node = path[indexLevel - 1];
        if (node.element == null || element.compareTo(node.element) != 0) {
            return false;
        }
        while (node != null) {
            if (element.equals(node.element)) {
                return true;
            }
            node = node.repeat;
        }
        return false;
    }

    /**
     * 获取元素迭代器
     *
     * @return 迭代器
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Node<E> c = null;
            Node<E> r = null;

            @Override
            public boolean hasNext() {
                if (c == null) {
                    return first != null;
                }
                return c.next != null || r.repeat != null;
            }

            @Override
            public E next() {
                if (c == null) {
                    c = first;
                    r = first;
                } else {
                    if (r.repeat != null) {
                        r = r.repeat;
                    } else {
                        c = c.next;
                        r = c;
                    }
                }
                return r.element;
            }
        };
    }

    /**
     * 将跳表转为数组
     *
     * @return 数组
     */
    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int i = 0;
        for (E item : this) {
            array[i++] = item;
        }
        return array;
    }

    /**
     * 将跳表转为数组
     *
     * @param array 数组模板
     * @return 数组
     */
    @Override
    public <T> T[] toArray(T[] array) {
        return (T[]) toArray();
    }

    /**
     * 添加一个元素到跳表中，如果跳表中已存在完全相同的元素，则不会做任何改变
     * <br> 该方法最终会调用元素的equals方法进行对比，来判断两个元素是否是完全相同的
     *
     * @param element 添加的元素，不能为空
     * @return 跳表是否被改变
     */
    @Override
    public boolean add(E element) {
        if (element == null) {
            return false;
        }

        // 查找元素
        Node<E>[] path = getPath(element);
        Node<E> node = path[indexLevel];

        // 已有相同值元素
        if (node.element != null && element.compareTo(node.element) == 0) {
            do {
                if (element.equals(node.element)) {
                    return false;
                }
            } while ((node = node.repeat) != null);
            path[indexLevel].repeat = new Node<>(element, null, null);
            size++;
            return true;
        }

        // 无相同值元素
        int level = randomLevel();
        Node<E>[] headerPath = getOrCreateHeaderPath(level);
        node = null;
        int diff = level - path.length + 1;
        for (int i = level; i >= 0; i--) {
            int pathIndex = i - diff;
            if (pathIndex >= 0) {
                node = new Node<>(element, path[pathIndex], path[pathIndex].next, node);
                path[pathIndex].next = node;
                if (node.down == null) {
                    last = node.next == null ? node : last;
                    first = path[pathIndex].element == null ? node : first;
                }
            } else {
                node = new Node<>(element, headerPath[i], headerPath[i].next, node);
                headerPath[i].next = node;
            }
            if (node.next != null) {
                node.next.prev = node;
            }
        }
        size++;

        return true;
    }

    /**
     * 从跳表中删除一个元素，如果跳表中不存在完全相同的元素，则不会做任何改变
     * <br> 该方法最终会调用元素的equals方法进行对比，来判断两个元素是否是完全相同的
     *
     * @param o 删除的元素，不能为空
     * @return 跳表是否被改变
     */
    @Override
    public boolean remove(Object o) {
        if (!(o instanceof Comparable) || isEmpty()) {
            return false;
        }

        E element = (E) o;
        Node<E>[] path = getPath(element);
        Node<E> node = path[indexLevel];

        if (node.element == null || element.compareTo(node.element) != 0) {
            return false;
        }

        Node<E> repeatUp = null;
        while (node != null) {
            if (element.equals(node.element)) {
                break;
            }
            repeatUp = node;
            node = node.repeat;
        }
        if (node == null) {
            return false;
        }

        if (repeatUp != null) {
            repeatUp.repeat = node.repeat;
        } else if (node.repeat != null) {
            node.element = node.repeat.element;
            node.repeat = node.repeat.repeat;
        } else {
            for (int i = path.length - 1; i >= 0; i--) {
                if (path[i].element != element) {
                    break;
                }
                path[i].prev.next = path[i].next;
                if (path[i].down == null) {
                    last = path[i].next == null ? path[i].prev : last;
                    first = path[i].prev.element == null ? path[i].next : first;
                }
            }
        }
        size--;

        return true;
    }

    /**
     * 判断跳表是否包含集合
     *
     * @param c 被包含的集合
     * @return 是否包含
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object e : c) {
            if (!contains(e)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 添加一整个集合的元素到跳表中
     *
     * @param c 添加的集合
     * @return 跳表是否被改变，只要集合中有任一元素被成功添加，则返回 true
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean result = false;
        for (E e : c) {
            if (add(e)) {
                result = true;
            }
        }
        return result;
    }

    /**
     * 从跳表冲移除一整个集合的元素
     *
     * @param c 移除的元素集合
     * @return 跳表是否被改变，只要集合中有任一元素被成功移除，则返回 true
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean result = false;
        for (Object e : c) {
            if (remove(e)) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    /**
     * 清空整个跳表，相当于重新new了一个跳表
     */
    @Override
    public void clear() {
        size = 0;
        indexLevel = 0;
        header = new Node<>(null, null, null);
        first = null;
        last = null;
    }

    /**
     * 移除跳表的第一个元素
     * <br> 跳表为空时会抛出 NoSuchElementException 异常
     *
     * @return 移除的元素
     */
    public E removeFirst() {
        E node = getFirst();
        remove(node);
        return node;
    }

    /**
     * 移除跳表的最后一个元素
     * <br> 跳表为空时会抛出 NoSuchElementException 异常
     *
     * @return 移除的元素
     */
    public E removeLast() {
        E node = getLast();
        remove(node);
        return node;
    }

    /**
     * 移除跳表的第一个元素
     *
     * @return 移除的元素，跳表为空时返回 null
     */
    public E pollFirst() {
        return peekFirst() == null ? null : removeFirst();
    }

    /**
     * 移除跳表的最后一个元素
     *
     * @return 移除的元素，跳表为空时返回 null
     */
    public E pollLast() {
        return peekLast() == null ? null : removeLast();
    }

    /**
     * 获取跳表中的第一个元素
     * <br> 跳表为空时会抛出 NoSuchElementException 异常
     *
     * @return 元素
     */
    public E getFirst() {
        if (first == null) {
            throw new NoSuchElementException();
        }
        return first.element;
    }

    /**
     * 获取跳表中的最后一个元素
     * <br> 跳表为空时会抛出 NoSuchElementException 异常
     *
     * @return 元素
     */
    public E getLast() {
        if (last == null) {
            throw new NoSuchElementException();
        }
        return last.element;
    }

    /**
     * 获取跳表中的第一个元素
     *
     * @return 元素，跳表为空时返回 null
     */
    public E peekFirst() {
        return first != null ? first.element : null;
    }

    /**
     * 获取跳表中的最后一个元素
     *
     * @return 元素，跳表为空时返回 null
     */
    public E peekLast() {
        return last != null ? last.element : null;
    }

    /**
     * 获取索引层级
     * <br> 注意：索引层级只会在添加元素时不断增加，删除的元素不会导致减少
     *
     * @return 索引层级
     */
    public int levels() {
        return this.indexLevel;
    }

    @Override
    public String toString() {
        String items;
        Iterator<E> it = iterator();
        if (!it.hasNext()) {
            items = "[]";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append('[');
            while (true) {
                E e = it.next();
                sb.append(e == this ? "(this Collection)" : e);
                if (!it.hasNext()) {
                    items = sb.append(']').toString();
                    break;
                }
                sb.append(',').append(' ');
            }
        }

        return String.format("Level=%s%s", levels(), items);
    }

    private Node<E>[] getPath(E element) {
        Node<E>[] path = new Node[indexLevel + 1];
        Node<E> c = header;
        for (int i = 0; i <= indexLevel; i++) {
            Node<E> node = forward(c, element);
            path[i] = node;
            if (node.down != null) {
                c = node.down;
            }
        }
        return path;
    }

    private Node<E> forward(Node<E> current, E element) {
        while (true) {
            // 如果当前是最后一个节点或小于下一个节点
            if (current.next == null || element.compareTo(current.next.element) < 0) {
                return current;
            }
            current = current.next;
        }
    }

    private int randomLevel() {
        int level = 0;
        while (level < MAX_INDEX_LEVEL) {
            if (ThreadLocalRandom.current().nextInt(100) < p) {
                level++;
                continue;
            }
            break;
        }
        return level;
    }

    public Node<E>[] getOrCreateHeaderPath(int level) {
        // 创建缺少的层数
        int absentLevels = level - indexLevel;
        for (int i = 0; i < absentLevels; i++) {
            header = new Node<>(null, null, null, header);
            indexLevel++;
        }

        Node<E> c = header;

        // 跳过层数
        int skipLevels = indexLevel - level;
        for (int i = 0; i < skipLevels; i++) {
            c = c.down;
        }

        // 拼接路径
        Node<E>[] path = new Node[level + 1];
        for (int i = 0; i <= level; i++) {
            path[i] = c;
            c = c.down;
        }

        return path;
    }

    @Override
    public SkipList<E> clone() {
        try {
            return (SkipList<E>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public static class Node<E extends Comparable<E>> {

        private E element;

        private Node<E> prev;

        private Node<E> next;

        private final Node<E> down;

        private Node<E> repeat;

        public Node(E element, Node<E> prev, Node<E> next) {
            this(element, prev, next, null);
        }

        public Node(E element, Node<E> prev, Node<E> next, Node<E> down) {
            this.element = element;
            this.prev = prev;
            this.next = next;
            this.down = down;
        }

    }

}
