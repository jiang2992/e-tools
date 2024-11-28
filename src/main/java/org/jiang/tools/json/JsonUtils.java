package org.jiang.tools.json;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jiang.tools.exception.SystemException;

/**
 * JSON 工具类
 *
 * @author Bin
 * @since 1.0.0
 */
public class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public static String toJson(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            throw new SystemException(e);
        }
    }

    public static <T> T toBean(String json, Class<T> cls) {
        try {
            return OBJECT_MAPPER.readValue(json, cls);
        } catch (Exception e) {
            throw new SystemException(e);
        }
    }

    public static <T> T toBean(Object obj, Class<T> cls) {
        return toBean(toJson(obj), cls);
    }

    public static <T> List<T> toList(String json, Class<T> cls) {
        List<?> list = toBean(json, List.class);
        return toList(list, cls);
    }

    public static <T> List<T> toList(List<?> list, Class<T> cls) {
        List<T> result = new ArrayList<>(list.size());
        list.forEach(obj -> result.add(toBean(obj, cls)));
        return result;
    }

    public static JsonNode readTree(byte[] bytes) {
        try {
            return OBJECT_MAPPER.readTree(bytes);
        } catch (IOException e) {
            throw new SystemException(e);
        }
    }

}
