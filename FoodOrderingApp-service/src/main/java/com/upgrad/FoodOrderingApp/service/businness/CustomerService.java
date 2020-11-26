/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: UserService.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import javax.validation.constraints.NotNull;

/**
 * Interface for user related services.
 */
public interface CustomerService {

  //SearchResult<CustomerEntity> findUsers(int page, int limit);

  //SearchResult<CustomerEntity> findUsers(UserStatus userStatus, int page, int limit);

  CustomerEntity findCustomerByEmail(@NotNull String emailAddress);

  CustomerEntity findCustomerByUuid(@NotNull String userUuid);

  //CustomerEntity createUser(@NotNull CustomerEntity newUser, @NotNull Integer roleUuid)
  // throws ApplicationException;

  CustomerEntity createCustomer(@NotNull CustomerEntity newUser);

  void updateCustomer(@NotNull String userUuid, @NotNull CustomerEntity updatedUser);

  //void changeUserStatus(@NotNull String userUuid, @NotNull UserStatus newUserStatus) throws
  // ApplicationException;

  void deleteCustomer(@NotNull String userUuid);

}