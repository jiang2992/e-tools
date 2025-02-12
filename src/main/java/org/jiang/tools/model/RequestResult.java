package org.jiang.tools.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jiang.tools.constants.StatusCodeConstants;
import org.jiang.tools.exception.SystemException;
import org.jiang.tools.json.JsonUtils;


/**
 * 接口调用结果
 *
 * @author Bin
 * @since 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestResult<T> implements Serializable {

    private String code;
    private T data;
    private String message;

    public RequestResult(String code) {
        this(code, null, null);
    }

    public static <T> RequestResult<T> of(String code, T data) {
        return new RequestResult<>(code, data, null);
    }

    public static <T> RequestResult<T> of(String code, T data, String message) {
        return new RequestResult<>(code, data, message);
    }

    public static RequestResult<?> success() {
        return RequestResult.success(null);
    }

    public static <T> RequestResult<T> success(T data) {
        return new RequestResult<>(StatusCodeConstants.SUCCEED, data, null);
    }

    public static RequestResult<?> fail() {
        return RequestResult.fail("系统服务异常");
    }

    public static RequestResult<?> fail(String message) {
        return new RequestResult<>(StatusCodeConstants.SYS_EXCEPTION, null, message);
    }

    public String toJson() {
        return JsonUtils.toJson(this);
    }

    public byte[] toBytes() {
        return this.toJson().getBytes();
    }

    /**
     * 检查请求结果是否成功
     *
     * @return 是否成功
     */
    @JsonIgnore
    public boolean isSuccess() {
        return StatusCodeConstants.SUCCEED.equals(this.code);
    }

    /**
     * 检查请求结果并返回数据
     *
     * @return 数据
     */
    @JsonIgnore
    public T getSuccessData() {
        return this.getSuccessData("request fail");
    }

    /**
     * 检查请求结果并返回数据
     *
     * @param message 错误消息
     * @return 数据
     */
    @JsonIgnore
    public T getSuccessData(String message) {
        if (this.isSuccess()) {
            return data;
        }
        throw new SystemException(String.format("%s：%s", message, this.toJson()));
    }

}
