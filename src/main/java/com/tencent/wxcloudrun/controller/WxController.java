package com.tencent.wxcloudrun.controller;

import cn.hutool.core.io.IoUtil;
import com.tencent.wxcloudrun.aes.WXBizMsgCrypt;
import com.tencent.wxcloudrun.service.msg.MsgService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * wx控制器
 */
@Slf4j
@RestController
@RequestMapping("/wx")
public class WxController {

    @Value("${wx.token}")
    private String token;
    @Value("${wx.encodingAesKey}")
    private String encodingAesKey;
    @Value("${wx.appId}")
    private String appId;

    @Resource
    private MsgService msgService;

    @GetMapping("/verifyUrl")
    public String verifyUrl(HttpServletRequest request) throws Exception {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        if (StringUtils.isNotBlank(echostr)) {
            WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(token, encodingAesKey, appId);
            return wxBizMsgCrypt.verifyUrl(signature, timestamp, nonce, echostr);
        }

        String xmlMsg = IoUtil.readUtf8(request.getInputStream());
        return msgService.getMsgAndReturn(xmlMsg);
    }

}
