package org.jiang.tools.json;

import java.util.Map;
import org.jiang.tools.text.StringUtils;
import org.jiang.tools.text.TemplateParser;

/**
 * Json模板解析器
 *
 * @author Bin
 * @since 1.1.3
 */
public class JsonTemplateParser extends TemplateParser {

    /**
     * 格式化并输出Map对象
     *
     * @param template 模板
     * @param obj      数据对象
     * @return Map对象
     */
    public Map<String, Object> formatToMap(String template, Object obj) {
        String str = this.format(template, obj);
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        return JsonUtils.toBean(str, Map.class);
    }

}
