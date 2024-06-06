package org.jiang.tools.text;

import org.jiang.tools.json.JsonUtils;
import org.jiang.tools.object.EasyResolver;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 模板解析器
 *
 * @author Bin
 * @see EasyResolver
 * @since 1.1.3
 */
public class TemplateParser {

    private static final String REGEX_ESCAPE_CHARS = "$()*+.[]?\\^{}|";
    public static final String EXP_START_DEFAULT = "${";
    public static final String EXP_END_DEFAULT = "}";

    private final String expStart;
    private final String expEnd;
    private final String expPlaceholder;

    public TemplateParser() {
        this(EXP_START_DEFAULT, EXP_END_DEFAULT);
    }

    /**
     * 指定表达式前缀和后缀来创建解析器
     *
     * @param expStart 表达式前缀
     * @param expEnd   表达式后缀
     */
    protected TemplateParser(String expStart, String expEnd) {
        this.expStart = expStart;
        this.expEnd = expEnd;
        this.expPlaceholder = "(?<=" + this.regexEscape(expStart) + ").*?(?=" + this.regexEscape(expEnd) + ")";
    }

    /**
     * 格式化并输出字符串
     *
     * @param template 模板
     * @param obj      数据对象
     * @return 字符串
     */
    public String format(String template, Object obj) {
        EasyResolver easyResolver = EasyResolver.of(obj);
        Matcher matcher = Pattern.compile(this.expPlaceholder).matcher(template);
        Map<String, String> matched = new HashMap<>(4);
        while (matcher.find()) {
            String exp = matcher.group();
            String fullExp = this.expStart + exp + this.expEnd;
            if (!matched.containsKey(fullExp)) {
                Object value = easyResolver.get(exp);
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
