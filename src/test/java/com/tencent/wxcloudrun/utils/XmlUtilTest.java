package com.tencent.wxcloudrun.utils;

import cn.hutool.core.util.XmlUtil;
import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.dto.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

/**
 * TODO....
 *
 * @date 2022/2/26
 * @author yaodongliu
 */
@Slf4j
class XmlUtilTest {
    @Test
    void xmlToBeanTest() {
        Document document = XmlUtil.parseXml("<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>12345678</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[你好]]></Content></xml>");
        TextMessage textMessage = XmlUtil.xmlToBean(document.getFirstChild(), TextMessage.class);
        log.info(JSON.toJSONString(textMessage));
    }

    @Test
    void beanToXmlTest() {
        TextMessage rsMsg = new TextMessage();
        rsMsg.setToUserName("toUser");
        rsMsg.setFromUserName("toUser");
        rsMsg.setCreateTime(System.currentTimeMillis());
        rsMsg.setMsgType("text");
        rsMsg.setContent("这是返回消息");
        Document rsDoc = XmlUtil.beanToXml(rsMsg);
        rsDoc.renameNode(rsDoc.getFirstChild(), null, "xml");
        log.info(XmlUtil.toStr(rsDoc));
    }

}
