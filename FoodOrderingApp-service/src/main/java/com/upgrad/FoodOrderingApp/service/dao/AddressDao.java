package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import java.util.List;
import javax.validation.constraints.NotNull;

public interface AddressDao extends BaseDao<AddressEntity> {

  StateEntity getStateByUUID(@NotNull String uuid);

  List<StateEntity> getAllStates();

  AddressEntity saveAddress(@NotNull AddressEntity addressEntity);
}
