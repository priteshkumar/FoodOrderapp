/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: UserService.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import com.upgrad.FoodOrderingApp.service.exception.UpdateCustomerException;
import javax.validation.constraints.NotNull;

/**
 * Interface for user related services.
 */
public interface CustomerService extends AuthenticationService {

  CustomerEntity findCustomerByUuid(@NotNull String userUuid);

  CustomerEntity findCustomerByContactNumber(@NotNull String contactNumber);

  CustomerEntity getCustomer(@NotNull String accessToken) throws AuthorizationFailedException;

  CustomerEntity saveCustomer(@NotNull CustomerEntity newUser) throws SignUpRestrictedException;

  CustomerAuthEntity authenticate(@NotNull String contactNumber, @NotNull String password)
      throws AuthenticationFailedException;

  CustomerAuthEntity logout(@NotNull String accessToken) throws AuthorizationFailedException;

  CustomerEntity updateCustomer(@NotNull CustomerEntity updatedUser);

  CustomerEntity updateCustomerPassword(@NotNull String oldPassword, @NotNull String newPassword,
      @NotNull CustomerEntity customerEntity) throws UpdateCustomerException;

}