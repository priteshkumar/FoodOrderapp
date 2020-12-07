package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

/*
interface for address related services
 */
public interface AddressService {

  StateEntity getStateByUUID(@NotNull String uuid) throws AddressNotFoundException;

  AddressEntity saveAddress(@NotNull AddressEntity addressEntity,
      @NotNull CustomerEntity customerEntity)
      throws SaveAddressException;
}
