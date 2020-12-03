/*
 * Copyright 2017-2018, Redux Software.
 *
 * File: BearerAuthDecoder.java
 * Date: Nov 10, 2017
 * Author: P7107311
 * URL: www.redux.com
 */
package com.upgrad.FoodOrderingApp.api.controller.provider;

import static com.upgrad.FoodOrderingApp.api.controller.data.ResourceConstants.BEARER_AUTH_PREFIX;

/**
 * Provider to decode bearer token.
 */
public class BearerAuthDecoder {

  private String accessToken;

  public BearerAuthDecoder(final String bearerToken) {
    if (!bearerToken.startsWith(BEARER_AUTH_PREFIX)) {
      //throw new UnauthorizedException(RestErrorCode.ATH_003);
      this.accessToken = bearerToken;
    }

    final String[] bearerTokens = bearerToken.split(BEARER_AUTH_PREFIX);
    if (bearerTokens.length == 2) {
      //throw new UnauthorizedException(RestErrorCode.ATH_004);
      this.accessToken = bearerTokens[1];
    }

  }

  public String getAccessToken() {
    return accessToken;
  }

}