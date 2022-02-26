package com.tencent.wxcloudrun.dto;

import lombok.Data;

/**
 * TODO....
 *
 * @date 2022/2/26
 * @author yaodongliu
 */
@Data
public abstract class BaseMessage {
    private String ToUserName;
    private String FromUserName;
    private long CreateTime;
    private String MsgType;
    private long MsgId;
}
