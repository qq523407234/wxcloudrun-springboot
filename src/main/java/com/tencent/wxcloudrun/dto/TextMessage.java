package com.tencent.wxcloudrun.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文本消息
 *
 * @date 2022/2/26
 * @author yaodongliu
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TextMessage extends BaseMessage {
    private String Content;
}
