package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerAddressEntity;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerAddressDaoImpl extends BaseDaoImpl<CustomerAddressEntity> implements
    CustomerAddressDao {

  public CustomerAddressEntity create(CustomerAddressEntity customerAddressEntity) {
    return super.create(customerAddressEntity);
  }

  @Override
  public List<CustomerAddressEntity> getAllCustomerAddress(@NotNull Integer customerId) {
    return entityManager.createNamedQuery("allAddressByCustomer", CustomerAddressEntity.class)
        .setParameter("customer_id", customerId).getResultList();
  }
}
