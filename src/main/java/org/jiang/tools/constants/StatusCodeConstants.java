package org.jiang.tools.constants;

/**
 * 状态码常量类
 *
 * @author Bin
 * @since 1.0.0
 */
public interface StatusCodeConstants {

    /**
     * 成功,正常返回数据
     */
    String SUCCEED = "200";

    /**
     * 请求参数缺少或不正确
     */
    String PARAM_LACK = "400";

    /**
     * 用户未认证
     */
    String UNAUTHORIZED = "401";

    /**
     * 操作权限不足
     */
    String ACCESS_DENIED = "403";

    /**
     * 找不到资源
     */
    String NOT_FOUND = "404";

    /**
     * 系统出现异常
     */
    String SYS_EXCEPTION = "500";

    /**
     * 本次操作需要验证码
     */
    String NEED_VERIFYCODE = "601";

}
