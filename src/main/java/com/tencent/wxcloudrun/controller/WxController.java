package com.tencent.wxcloudrun.controller;

import cn.hutool.core.io.IoUtil;
import com.tencent.wxcloudrun.aes.WXBizMsgCrypt;
import com.tencent.wxcloudrun.service.msg.MsgService;
import com.tencent.wxcloudrun.service.token.TokenUtilService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Resource
    private TokenUtilService tokenUtilService;

    @GetMapping("/verifyUrl")
    public String verifyUrl(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr)
            throws Exception {
        WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(token, encodingAesKey, appId);
        return wxBizMsgCrypt.verifyUrl(signature, timestamp, nonce, echostr);
    }

    @PostMapping("/verifyUrl")
    public String verifyUrl(HttpServletRequest request) throws Exception {
        String xmlMsg = IoUtil.readUtf8(request.getInputStream());
        return msgService.getMsgAndReturn(xmlMsg);
    }

    @GetMapping("/getToken")
    public String getToken(@RequestParam("pw") String pw) {
        String token = "index";
        if ("simplecheck".equals(pw)) {
            token = tokenUtilService.getToken();
        }
        return token;
    }

}
