package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.aes.AesException;
import com.tencent.wxcloudrun.aes.WXBizMsgCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * index控制器
 */
@Controller
public class IndexController {

    @Value("${wx.token}")
    private String token;
    @Value("${wx.encodingAesKey}")
    private String encodingAesKey;
    @Value("${wx.appId}")
    private String appId;

    /**
     * 主页页面
     * @return API response html
     */
    @GetMapping
    public String index(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr)
            throws AesException {
        WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(token, encodingAesKey, appId);
        return wxBizMsgCrypt.verifyUrl(signature, timestamp, nonce, echostr);
    }

}
