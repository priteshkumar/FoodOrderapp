/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: RequestContext.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package com.upgrad.FoodOrderingApp.service.common;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Object containing request context information.
 */
public interface RequestContext extends Serializable {

  String getRequestId();

  void setRequestId(String requestId);

  ZonedDateTime getRequestTime();

  void setRequestTime(ZonedDateTime requestTime);

  String getClientId();

  void setClientId(String clienId);

  String getOriginIpAddress();

  void setOriginIpAddress(String ipAddress);

  String getUserId();

  void setUserId(String userId);

  String getAccessedResource();

  void setAccessedResource(String requestedResource);

}