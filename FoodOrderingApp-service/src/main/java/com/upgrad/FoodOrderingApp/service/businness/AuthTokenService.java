package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import javax.validation.constraints.NotNull;

/**
 * Interface for user authentication related services.
 */
public interface AuthTokenService {

  CustomerAuthEntity issueToken(@NotNull CustomerEntity customerEntity);

  //void invalidateToken(@NotNull String accessToken);

  //CustomerAuthToken validateToken(@NotNull String accessToken);

}