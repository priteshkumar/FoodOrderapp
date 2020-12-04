package com.upgrad.FoodOrderingApp.service.dao;

import static com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity.BY_ACCESS_TOKEN;
import static com.upgrad.FoodOrderingApp.service.entity.StateEntity.BY_STATE_UUID;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import javax.persistence.NoResultException;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AddressDaoImpl extends BaseDaoImpl<AddressEntity> implements AddressDao {

  @Override
  public StateEntity getStateByUUID(@NotNull String uuid) {
    try {
      return entityManager.createNamedQuery(BY_STATE_UUID, StateEntity.class)
          .setParameter("uuid", uuid).getSingleResult();
    } catch (NoResultException nre) {
      return null;
    }
  }

  @Override
  public AddressEntity saveAddress(@NotNull AddressEntity addressEntity) {
    return super.create(addressEntity);
  }
}
