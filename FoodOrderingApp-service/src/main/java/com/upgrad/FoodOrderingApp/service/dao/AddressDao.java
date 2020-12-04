package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import javax.validation.constraints.NotNull;

public interface AddressDao extends BaseDao<AddressEntity> {

  StateEntity getStateByUUID(@NotNull String uuid);

  AddressEntity saveAddress(@NotNull AddressEntity addressEntity);
}
