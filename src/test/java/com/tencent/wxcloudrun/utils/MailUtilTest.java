package com.tencent.wxcloudrun.utils;

import cn.hutool.extra.mail.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * 邮件test
 *
 * @date 2022/2/26
 * @author yaodongliu
 */
@Slf4j
class MailUtilTest {
    @Test
    void xmlToBeanTest() {
        MailUtil.send("yaodongliu@88.com", "微信留言", "test", false);
    }

}
