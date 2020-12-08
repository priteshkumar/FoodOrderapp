package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

/*
interface for address related services
 */
public interface AddressService {

  StateEntity getStateByUUID(final String uuid) throws AddressNotFoundException;

  AddressEntity getAddressByUUID(@NotNull String uuid, @NotNull CustomerEntity customerEntity)
      throws AddressNotFoundException, AuthorizationFailedException;

  AddressEntity saveAddress(@NotNull AddressEntity addressEntity,
      @NotNull CustomerEntity customerEntity)
      throws SaveAddressException;

  List<AddressEntity> getAllAddress(@NotNull CustomerEntity customerEntity);

  AddressEntity deleteAddress(@NotNull AddressEntity addressEntity);
}
