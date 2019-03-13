package com.xwszt.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义异常
 *
 * @author xwszt
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomException extends RuntimeException {
    /**
     * 异常状态码
     */
    private String code;
    /**
     * 异常信息
     */
    private String message;
    /**
     * 发生异常的方法、位置
     */
    private String method;
    /**
     * 异常描述
     */
    private String descInfo;
}
