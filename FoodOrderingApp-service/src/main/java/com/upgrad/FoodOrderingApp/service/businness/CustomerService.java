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
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import javax.validation.constraints.NotNull;

/**
 * Interface for user related services.
 */
public interface CustomerService extends AuthenticationService {

  CustomerEntity findCustomerByUuid(@NotNull String userUuid);

  CustomerEntity findCustomerByContactNumber(@NotNull String contactNumber);

  CustomerEntity saveCustomer(@NotNull CustomerEntity newUser) throws SignUpRestrictedException;

  CustomerAuthEntity authenticate(@NotNull String contactNumber, @NotNull String password)
      throws AuthenticationFailedException;

  void updateCustomer(@NotNull String userUuid, @NotNull CustomerEntity updatedUser);

  //void changeUserStatus(@NotNull String userUuid, @NotNull UserStatus newUserStatus) throws
  // ApplicationException;
  void deleteCustomer(@NotNull String userUuid);

}