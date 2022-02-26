package com.tencent.wxcloudrun.model;

import java.util.Date;
import javax.persistence.Table;
import lombok.Data;

@Data
@Table(name = "access_token_management")
public class AccessTokenManagement {

  private String appId;

  private String accessToken;

  private Date inTime;

  private Date outTime;
}
