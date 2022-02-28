package com.tencent.wxcloudrun.service.mail;

import cn.hutool.extra.mail.GlobalMailAccount;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 消息处理
 *
 * @date 2022/2/26
 * @author yaodongliu
 */
@Service
public class MailService {

    @Value("${hutool.mail.pass}")
    private String pass;

    public void send(String content) {
        send("微信留言", content, false);
    }

    public void send(String subject, String content, boolean isHtml) {
        MailAccount mailAccount = GlobalMailAccount.INSTANCE.getAccount();
        mailAccount.setPass(pass);

        MailUtil.send(mailAccount, "yaodongliu@88.com", subject, content, isHtml);
    }

}
