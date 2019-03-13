package com.xwszt.backend.web.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 返回给客户端的数据结构
 *
 * @author xwszt
 */
@Data
public class Json implements Serializable {
    /**
     * 代码
     */
    private String code;

    /**
     * 消息
     */
    private String msg;

    /**
     * 具体业务数据
     */
    private Object data;
}
