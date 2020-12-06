package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerAddressEntity;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerAddressDaoImpl extends BaseDaoImpl<CustomerAddressEntity> implements
    CustomerAddressDao {

  public CustomerAddressEntity create(CustomerAddressEntity customerAddressEntity) {
    return super.create(customerAddressEntity);
  }
}
