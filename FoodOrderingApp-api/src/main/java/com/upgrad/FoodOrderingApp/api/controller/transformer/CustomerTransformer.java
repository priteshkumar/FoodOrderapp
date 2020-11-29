package com.upgrad.FoodOrderingApp.api.controller.transformer;

import com.upgrad.FoodOrderingApp.api.model.SignupCustomerRequest;
import com.upgrad.FoodOrderingApp.api.model.SignupCustomerResponse;
import com.upgrad.FoodOrderingApp.api.model.UpdateCustomerRequest;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;

public final class CustomerTransformer {

  public static CustomerEntity toEntity(SignupCustomerRequest customerRequest)
      throws SignUpRestrictedException {
    if (StringUtils.isEmpty(customerRequest.getFirstName()) || StringUtils
        .isEmpty(customerRequest.getEmailAddress()) || StringUtils
        .isEmpty(customerRequest.getPassword())
        || StringUtils.isEmpty(customerRequest.getContactNumber())) {
      throw new SignUpRestrictedException("SGR-005",
          "Except last name all fields should be filled");
    }
    CustomerEntity CustomerEntity = new CustomerEntity();

    CustomerEntity.setFirstName(customerRequest.getFirstName());
    if (customerRequest.getLastName() != null) {
      CustomerEntity.setLastName(customerRequest.getLastName());
    }
    CustomerEntity.setEmail(customerRequest.getEmailAddress());
    CustomerEntity.setPassword(customerRequest.getPassword());
    CustomerEntity.setContact_number(customerRequest.getContactNumber());
    CustomerEntity.setUuid(UUID.randomUUID().toString());
    return CustomerEntity;
  }

  public static CustomerEntity toEntity(UpdateCustomerRequest customerRequest) {
    CustomerEntity CustomerEntity = new CustomerEntity();
    CustomerEntity.setFirstName(customerRequest.getFirstName());
    CustomerEntity.setLastName(customerRequest.getLastName());
    return CustomerEntity;
  }

  public static SignupCustomerResponse toSignupResponse(CustomerEntity CustomerEntity) {
    return new SignupCustomerResponse().id(CustomerEntity.getUuid())
        .status("CUSTOMER SUCCESSFULLY REGISTERED");
  }

}