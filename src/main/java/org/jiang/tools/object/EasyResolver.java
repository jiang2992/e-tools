package org.jiang.tools.object;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.regex.Pattern;
import org.jiang.tools.collection.CollectionUtils;
import org.jiang.tools.exception.BadArgumentException;
import org.jiang.tools.exception.PropertyExtractException;
import org.jiang.tools.text.StringUtils;

/**
 * EasyResolver：提供对象的解析操作
 * <p>
 * 对象属性获取：支持使用表达式获取对象的特定属性，支持普通对象、List、Map，默认会对已经获取的属性进行缓存
 * </p>
 * 表达式语法示例：$.user.name | $.userList.5.name | $.userList.$first.dept.name
 *
 * @author Bin
 * @since 1.1.3
 */
public class EasyResolver {

    private static final String EXP_ROOT_SYMBOL = "$";
    private static final String EXP_FUN_SYMBOL = "$";
    private static final String EXP_SEPARATOR = ".";

    private static final Map<String, BiFunction<EasyResolver, Object, Object>> FUN_NAME_MAPPER;

    static {
        FUN_NAME_MAPPER = new HashMap<>(2);
        FUN_NAME_MAPPER.put("first", EasyResolver::firstFun);
        FUN_NAME_MAPPER.put("last", EasyResolver::lastFun);
    }

    private final Object sourceObject;
    private Reference<Map<String, Object>> cacheMap;

    public EasyResolver(Object obj) {
        if (obj == null) {
            throw new BadArgumentException();
        }
        this.sourceObject = obj;
        enableCache();
    }

    public static EasyResolver of(Object obj) {
        return new EasyResolver(obj);
    }

    /**
     * 启用缓存
     *
     * @return EasyResolver
     */
    public EasyResolver enableCache() {
        if (this.cacheMap == null || this.cacheMap.get() == null) {
            this.cacheMap = new SoftReference<>(new HashMap<>());
        }
        return this;
    }

    /**
     * 禁用缓存
     *
     * @return EasyResolver
     */
    public EasyResolver disableCache() {
        this.cacheMap.clear();
        return this;
    }

    /**
     * 获取对象属性
     *
     * @param exp 表达式
     * @return 属性值
     */
    public Object get(String exp) {
        if (!verifyExp(exp)) {
            return new PropertyExtractException();
        }
        if (exp.equals(EXP_ROOT_SYMBOL)) {
            return this.sourceObject;
        }

        // 获取缓存
        if (cacheMap.get() != null) {
            Object cacheObj = Objects.requireNonNull(cacheMap.get()).get(exp);
            if (cacheObj != null) {
                return cacheObj;
            }
        }

        // 循环获取属性
        Object currentObject = this.sourceObject;
        String[] elms = exp.substring((EXP_ROOT_SYMBOL + EXP_SEPARATOR).length()).split(Pattern.quote(EXP_SEPARATOR));
        for (String elm : elms) {
            currentObject = getProperty(currentObject, elm);
        }

        // 设置缓存
        if (cacheMap.get() != null) {
            Objects.requireNonNull(cacheMap.get()).put(exp, currentObject);
        }

        return currentObject;
    }

    private Object getProperty(Object obj, String property) {
        if (obj == null) {
            return new PropertyExtractException("object is null");
        }

        if (property.startsWith(EXP_FUN_SYMBOL)) {
            return FUN_NAME_MAPPER.get(property.substring(EXP_FUN_SYMBOL.length())).apply(this, obj);
        }
        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).get(property);
        }
        if (obj instanceof Collection) {
            int index = Integer.parseInt(property);
            return CollectionUtils.getElement((Collection<?>) obj, index);
        }

        try {
            return ReflectionUtils.getFieldValue(obj, property);
        } catch (NoSuchFieldException e) {
            throw new PropertyExtractException(String.format("no such field [%s]", property));
        } catch (IllegalAccessException e) {
            throw new PropertyExtractException(String.format("get field fail [%s]", property));
        }
    }

    /**
     * 函数：获取第一个元素
     *
     * @param obj 对象
     * @return 元素
     */
    private Object firstFun(Object obj) {
        if (!(obj instanceof Collection)) {
            return new PropertyExtractException("object is not collection");
        }
        return CollectionUtils.getFirstElement((Collection<?>) obj);
    }

    /**
     * 函数：获取最后一个元素
     *
     * @param obj 对象
     * @return 元素
     */
    private Object lastFun(Object obj) {
        if (!(obj instanceof Collection)) {
            return new PropertyExtractException("object is not collection");
        }
        return CollectionUtils.getLastElement((Collection<?>) obj);
    }

    /**
     * 校验表达式语法
     *
     * @param exp 表达式
     * @return 是否符合
     */
    public static boolean verifyExp(String exp) {
        if (StringUtils.isEmpty(exp)) {
            return false;
        }
        if (exp.equals(EXP_ROOT_SYMBOL)) {
            return true;
        }
        String prefix = EXP_ROOT_SYMBOL + EXP_SEPARATOR;
        if (!exp.startsWith(prefix) || prefix.length() == exp.length()) {
            return false;
        }
        exp = exp.substring(prefix.length());
        String[] elms = exp.split(Pattern.quote(EXP_SEPARATOR));
        for (String elm : elms) {
            if (elm.isEmpty()) {
                return false;
            }
            if (!elm.startsWith(EXP_FUN_SYMBOL)) {
                continue;
            }
            if (elm.length() == EXP_FUN_SYMBOL.length()) {
                return false;
            }
            if (!FUN_NAME_MAPPER.containsKey(elm.substring(EXP_FUN_SYMBOL.length()))) {
                return false;
            }
        }
        return true;
    }

}
