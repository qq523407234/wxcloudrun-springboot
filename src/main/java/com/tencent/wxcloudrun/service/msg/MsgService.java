package com.tencent.wxcloudrun.service.msg;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.XmlUtil;
import com.tencent.wxcloudrun.dto.BaseMessage;
import com.tencent.wxcloudrun.dto.TextMessage;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * TODO....
 *
 * @date 2022/2/26
 * @author yaodongliu
 */
@Service
public class MsgService {

    private Map<String, Object> getMessageBean(String msg) {
        Document document = XmlUtil.parseXml(msg);
        Node msgNode = XmlUtil.getNodeByXPath("//xml", document);
        return XmlUtil.xmlToMap(msgNode);
    }

    private String getMsgByBean(BaseMessage msg) {
        Document doc = XmlUtil.beanToXml(msg);
        doc.renameNode(doc.getFirstChild(), null, "xml");
        return XmlUtil.toStr(doc);
    }

    public String getMsgAndReturn(String msg) {
        Map<String, Object> msgMap = getMessageBean(msg);
        if ("text".equals(msgMap.get("MsgType"))) {
            TextMessage fromMsg = BeanUtil.toBean(msgMap, TextMessage.class);

            TextMessage rsMsg = new TextMessage();
            rsMsg.setToUserName(fromMsg.getFromUserName());
            rsMsg.setFromUserName(fromMsg.getToUserName());
            rsMsg.setCreateTime(System.currentTimeMillis());
            rsMsg.setMsgType("text");
            rsMsg.setContent("这是返回消息");
            return getMsgByBean(rsMsg);
        } else {
            return StringUtils.EMPTY;
        }
    }

}
