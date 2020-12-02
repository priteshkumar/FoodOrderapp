package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import javax.validation.constraints.NotNull;

/**
 * Interface for authentication related services.
 */
public interface AuthenticationService {

  CustomerAuthEntity authenticate(@NotNull String username, @NotNull String password)
      throws AuthenticationFailedException;

  CustomerAuthEntity logout(@NotNull String accessToken) throws AuthorizationFailedException;
}