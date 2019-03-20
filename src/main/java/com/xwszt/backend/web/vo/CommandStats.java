package com.xwszt.backend.web.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xwszt
 */
@Data
public class CommandStats implements Serializable {
    /**
     * 节点，包括IP和端口号
     */
    private String node;
    /**
     * 命令名称
     */
    private String cmdName;
    /**
     * 使用次数
     */
    private String count;
    /**
     * 总共消耗的CPU时长，单位微秒
     */
    private String totalCpuUseTime;
    /**
     * 平均每次消耗的CPU时长,单位微秒
     */
    private String perCpuUseTime;
}
