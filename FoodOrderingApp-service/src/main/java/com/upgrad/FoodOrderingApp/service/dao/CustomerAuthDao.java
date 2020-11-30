/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: UserAuthDao.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import java.time.ZonedDateTime;
import javax.validation.constraints.NotNull;

/**
 * DAO interface for {@link CustomerAuthEntity}.
 */
public interface CustomerAuthDao extends BaseDao<CustomerAuthEntity> {

  CustomerAuthEntity findToken(@NotNull String accessToken);

  CustomerAuthEntity findActiveTokenByUser(@NotNull long userId, @NotNull ZonedDateTime currentAt);

}