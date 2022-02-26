package com.tencent.wxcloudrun.service.token;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.NotInitedException;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.dao.AccessTokenManagementMapper;
import com.tencent.wxcloudrun.model.AccessTokenManagement;
import java.util.Date;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * access_token工具类
 *
 * @date 2022/2/26
 * @author yaodongliu
 */
@Slf4j
@Service
public class TokenUtilService {

    @Resource
    private AccessTokenManagementMapper accessTokenManagementMapper;

    @Value("${wx.appId}")
    private String appId;
    @Value("${wx.appSecret}")
    private String appSecret;

    private String accessToken = null;
    private Date inTime = null;
    private Date outTime = null;

    private boolean checkTokenActive() {
        return checkTokenActive(accessToken, inTime, outTime);
    }

    private boolean checkTokenActive(AccessTokenManagement accessTokenManagement) {
        if (accessTokenManagement == null) {
            return false;
        }
        return checkTokenActive(accessTokenManagement.getAccessToken(), accessTokenManagement.getInTime(), accessTokenManagement.getOutTime());
    }

    public boolean checkTokenActive(String tmpToken, Date tmpInTime, Date tmpOutTime) {
        if (tmpToken == null) {
            return false;
        }
        if (tmpInTime == null || tmpOutTime == null) {
            return false;
        }
        Date now = new Date();
        return now.compareTo(tmpInTime) > 0 && now.compareTo(tmpOutTime) < 0;
    }

    public String getToken() {
        if (checkTokenActive()) {
            return accessToken;
        }
        synchronized (this) {
            if (getTokenByDB()) {
                return accessToken;
            }
            if (getTokenByUrl()) {
                return accessToken;
            }
        }
        throw new NotInitedException("无法获取有效的accessToken");
    }

    private boolean getTokenByDB() {
        AccessTokenManagement accessTokenManagement = accessTokenManagementMapper.selectByPrimaryKey(appId);
        if (checkTokenActive(accessTokenManagement)) {
            accessToken = accessTokenManagement.getAccessToken();
            inTime = accessTokenManagement.getInTime();
            outTime = accessTokenManagement.getOutTime();
            return true;
        }
        return false;
    }

    private boolean getTokenByUrl() {
        String getTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + appSecret;
        Date now = new Date();
        String rs = HttpUtil.get(getTokenUrl);
        if (StringUtils.isBlank(rs)) {
            return false;
        }
        JSONObject rsJson = JSON.parseObject(rs);
        String tmpToken = rsJson.getString("access_token");
        if (StringUtils.isNotBlank(tmpToken)) {
            int expiresIn = rsJson.getInteger("expires_in");
            Date offsetDate = DateUtil.offsetMillisecond(now, expiresIn);
            accessToken = tmpToken;
            inTime = now;
            outTime = offsetDate;

            saveToken(tmpToken, now, offsetDate);
            return true;
        }
        log.error("errcode：{},errmsg:{}", rsJson.getString("errcode"), rsJson.getString("errmsg"));
        throw new HttpException(rsJson.getString("errmsg"));
    }

    private void saveToken(String tmpToken, Date tmpInTime, Date tmpOutTime) {
        AccessTokenManagement accessTokenManagement = new AccessTokenManagement();
        accessTokenManagement.setAppId(appId);
        accessTokenManagement.setAccessToken(tmpToken);
        accessTokenManagement.setInTime(tmpInTime);
        accessTokenManagement.setOutTime(tmpOutTime);
        if (accessTokenManagementMapper.selectByPrimaryKey(appId) == null) {
            accessTokenManagementMapper.insert(accessTokenManagement);
        } else {
            accessTokenManagementMapper.updateByPrimaryKey(accessTokenManagement);
        }
    }

}
