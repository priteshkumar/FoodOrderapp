/*
 * Copyright 2017-2018, Redux Software.
 *
 * File: BasicAuthProvider.java
 * Date: Oct 30, 2017
 * Author: P7107311
 * URL: www.redux.com
 */
package com.upgrad.FoodOrderingApp.api.controller.provider;

import static com.upgrad.FoodOrderingApp.api.controller.data.ResourceConstants.BASIC_AUTH_PREFIX;
import static com.upgrad.FoodOrderingApp.api.controller.data.ResourceConstants.BEARER_AUTH_PREFIX;

import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import java.util.Base64;

/**
 * Provider to decode basic auth credentials.
 */
public final class BasicAuthDecoder {

  private String username;
  private String password;

  public BasicAuthDecoder(final String base64EncodedCredentials)
      throws AuthenticationFailedException {
    if (!base64EncodedCredentials.startsWith(BASIC_AUTH_PREFIX)) {
      throw new AuthenticationFailedException("ATH-003",
          "Incorrect format of decoded customer name and password");
    }
    final String[] base64Decoded = new String(
        Base64.getDecoder().decode(base64EncodedCredentials.split("Basic ")[1])).split(":");
    if (base64Decoded.length != 2) {
      throw new AuthenticationFailedException("ATH-003",
          "Incorrect format of decoded customer name and password");
    }
    this.username = base64Decoded[0];
    this.password = base64Decoded[1];
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

}