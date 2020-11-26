/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: CustomerDao.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import javax.validation.constraints.NotNull;

/**
 * DAO abstraction for {@link CustomerEntity}.
 */
public interface CustomerDao extends BaseDao<CustomerEntity> {

  CustomerEntity findByEmail(@NotNull String email);

  // SearchResult<CustomerEntity> findCustomers(CustomerStatus CustomerStatus, int offset,
  //   int limit);

  //SearchResult<CustomerEntity> findCustomers(int offset, int limit);

}