package org.jiang.tools.util;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jiang.tools.exception.SystemException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * JSON 工具类
 *
 * @author Bin
 * @date 2019-10-15 09:12
 */
public class JsonUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new SystemException(e);
        }
    }

    public static <T> T toBean(String json, Class<T> cls) {
        try {
            return objectMapper.readValue(json, cls);
        } catch (Exception e) {
            throw new SystemException(e);
        }
    }

    public static <T> T toBean(Object obj, Class<T> cls) {
        return toBean(toJson(obj), cls);
    }

    public static <T> List<T> toList(String json, Class<T> cls) {
        List list = toBean(json, List.class);
        return toList(list, cls);
    }

    public static <T> List<T> toList(List list, Class<T> cls) {
        List<T> result = new ArrayList<>(list.size());
        list.forEach(obj -> result.add(toBean(obj, cls)));
        return result;
    }

    public static JsonNode readTree(byte[] bytes) {
        try {
            return objectMapper.readTree(bytes);
        } catch (IOException e) {
            throw new SystemException(e);
        }
    }

}
