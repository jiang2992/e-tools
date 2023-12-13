package org.jiang.tools.template;

import org.jiang.tools.exception.SystemException;
import org.jiang.tools.util.JsonUtils;
import org.jiang.tools.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 对象属性工具类
 *
 * @author Bin
 * @date 2021/9/28 16:18
 */
public class ObjectPropertyUtils {

    public static final String EXP_ROOT = "$";
    public static final String EXP_SEPARATOR = "\\.";
    public static final String EXP_EQUAL = "=";

    public static Object extract(Object obj, String exp) {
        if (obj == null || StringUtils.isEmpty(exp)) {
            return null;
        }
        if (EXP_ROOT.equals(exp)) {
            return obj;
        }

        Map map;
        if (obj instanceof Map) {
            map = (Map) obj;
        } else {
            map = JsonUtils.toBean(obj, Map.class);
        }

        if (map == null || map.size() == 0) {
            return null;
        }

        Object tier;
        String[] keys = exp.split(EXP_SEPARATOR);
        if (EXP_ROOT.equals(keys[0])) {
            tier = new HashMap<>(1);
            ((Map) tier).put(EXP_ROOT, map);
        } else {
            tier = map;
        }

        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            if (!(tier instanceof Map)) {
                return null;
            }
            Map tierMap = (Map) tier;
            tier = tierMap.get(key);
        }

        return tier;
    }

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

