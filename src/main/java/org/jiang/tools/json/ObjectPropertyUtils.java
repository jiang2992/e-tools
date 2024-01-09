package org.jiang.tools.json;

import org.jiang.tools.exception.SystemException;
import org.jiang.tools.text.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 对象属性工具类
 *
 * @author Bin
 * @since 1.0.0
 */
public class ObjectPropertyUtils {

    public static final String EXP_ROOT = "$";
    public static final String EXP_SEPARATOR = "\\.";
    public static final String EXP_EQUAL = "=";

    /**
     * 根据表达式提取对象属性值=
     *
     * @param obj 数据对象，支持普通对象和Map（暂不支持集合）
     * @param exp 表达式（示例：$.user.id）
     * @return 对象属性值
     */
    public static Object extract(Object obj, String exp) {
        if (obj == null || StringUtils.isEmpty(exp)) {
            return null;
        }
        if (EXP_ROOT.equals(exp)) {
            return obj;
        }

        // 统一将数据对象转为map
        Map<?, ?> dataMap;
        if (obj instanceof Map) {
            dataMap = (Map<?, ?>) obj;
        } else {
            dataMap = JsonUtils.toBean(obj, Map.class);
        }

        if (dataMap == null || dataMap.isEmpty()) {
            return null;
        }

        // 当前层级的数据对象
        Object tier;

        String[] keys = exp.split(EXP_SEPARATOR);
        if (EXP_ROOT.equals(keys[0])) {
            Map<String, Object> map = new HashMap<>(1);
            map.put(EXP_ROOT, dataMap);
            tier = map;
        } else {
            tier = dataMap;
        }

        // 逐层取出
        for (String key : keys) {
            if (!(tier instanceof Map)) {
                return null;
            }
            Map<?, ?> tierMap = (Map<?, ?>) tier;
            tier = tierMap.get(key);
        }

        return tier;
    }

    /**
     * 判断对象属性值等式是否成立
     *
     * @param obj 数据对象，支持普通对象和Map（暂不支持集合）
     * @param exp 表达式
     * @return 是否成立
     */
    public static boolean judge(Object obj, String exp) {
        String[] strings = exp.split(EXP_EQUAL);
        if (strings.length == 0) {
            throw new SystemException(String.format("exp format error [%s]", exp));
        }
        String leftValue = String.valueOf(extract(obj, strings[0]));
        String rightValue = strings[1];
        return leftValue.equals(rightValue);
    }

}

