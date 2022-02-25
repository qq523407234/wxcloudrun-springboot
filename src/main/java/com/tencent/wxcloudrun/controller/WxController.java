package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.aes.AesException;
import com.tencent.wxcloudrun.aes.WXBizMsgCrypt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/verifyUrl")
    public String verifyUrl(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr)
            throws AesException {
        WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(token, encodingAesKey, appId);
        return wxBizMsgCrypt.verifyUrl(signature, timestamp, nonce, echostr);
    }

}
