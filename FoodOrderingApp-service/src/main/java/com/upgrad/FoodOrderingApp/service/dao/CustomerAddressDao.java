package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerAddressEntity;

public interface CustomerAddressDao extends BaseDao<CustomerAddressEntity> {

  public CustomerAddressEntity create(CustomerAddressEntity customerAddressEntity);
}
