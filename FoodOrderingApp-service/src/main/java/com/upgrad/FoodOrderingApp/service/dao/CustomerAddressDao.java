package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerAddressEntity;
import java.util.List;
import javax.validation.constraints.NotNull;

public interface CustomerAddressDao extends BaseDao<CustomerAddressEntity> {

  public CustomerAddressEntity create(CustomerAddressEntity customerAddressEntity);

  public List<CustomerAddressEntity> getAllCustomerAddress(@NotNull Integer customerId);
}
