package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import javax.validation.constraints.NotNull;

/**
 * Interface for user authentication related services.
 */
public interface AuthTokenService {

  CustomerAuthEntity issueToken(@NotNull CustomerEntity customerEntity);

  CustomerAuthEntity invalidateToken(@NotNull String accessToken)
      throws AuthorizationFailedException;

  CustomerAuthEntity validateToken(@NotNull String accessToken) throws AuthorizationFailedException;

}