/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: BaseDao.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package com.upgrad.FoodOrderingApp.service.dao;

/**
 * TODO: Provide javadoc
 */
public interface BaseDao<E> {

  E create(E e);

  E update(E e);

  E findById(final Object id);

  E findByUUID(final Object uuid);

}