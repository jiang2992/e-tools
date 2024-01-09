package org.jiang.tools.json;

import org.jiang.tools.text.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Json模板解析器
 * 示例：
 * template: {id:1001,name:"${name}"}
 * data: {name:"lisi"}
 * ↓↓↓
 * result: {id:1001,name:"lisi"}
 *
 * @author Bin
 * @since 1.0.0
 */
public class JsonTemplateParser {

    private static final String REGEX_ESCAPE_CHARS = "$()*+.[]?\\^{}|";
    public static final String EXP_START_DEFAULT = "${";
    public static final String EXP_END_DEFAULT = "}";

    private final String expStart;
    private final String expEnd;
    private final String expPlaceholder;

    public JsonTemplateParser() {
        this(EXP_START_DEFAULT, EXP_END_DEFAULT);
    }

    public JsonTemplateParser(String expStart, String expEnd) {
        this.expStart = expStart;
        this.expEnd = expEnd;
        this.expPlaceholder = "(?<=" + this.regexEscape(expStart) + ").*?(?=" + this.regexEscape(expEnd) + ")";
    }

    public Map<String, Object> parsingToMap(String template, Object obj) {
        String str = this.parsingToString(template, obj);
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        return JsonUtils.toBean(str, Map.class);
    }

    public String parsingToString(String template, Object obj) {
        Matcher matcher = Pattern.compile(this.expPlaceholder).matcher(template);
        Map<String, String> matched = new HashMap<>(4);
        while (matcher.find()) {
            String exp = matcher.group();
            String fullExp = this.expStart + exp + this.expEnd;
            if (!matched.containsKey(fullExp)) {
                Object value = ObjectPropertyUtils.extract(obj, exp);
                String strValue = this.objectToString(value);
                matched.put(fullExp, strValue);
            }
        }
        for (String key : matched.keySet()) {
            String value = matched.get(key);
            template = template.replace(key, value);
        }
        return template;
    }

    private String objectToString(Object obj) {
        if (obj == null) {
            return "null";
        }
        Class<?> cls = obj.getClass();
        if (cls.isPrimitive()) {
            return obj.toString();
        }
        if (cls.isAssignableFrom(Number.class)) {
            return obj.toString();
        }
        if (obj instanceof CharSequence) {
            return obj.toString();
        }
        return JsonUtils.toJson(obj);
    }

    private String regexEscape(String str) {
        StringBuilder result = new StringBuilder();
        char[] cs = str.toCharArray();
        for (char c : cs) {
            if (REGEX_ESCAPE_CHARS.indexOf(c) != -1) {
                result.append("\\");
                result.append(c);
            }
        }
        return result.toString();
    }

}
