package com.upgrad.FoodOrderingApp.service.dao;

import static com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity.BY_ACCESS_TOKEN;
import static com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity.BY_CUSTOMER;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import java.time.ZonedDateTime;
import javax.persistence.NoResultException;
import org.springframework.stereotype.Repository;

/**
 * Implementation of {@link CustomerAuthDao}.
 */
@Repository
public class CustomerAuthDaoImpl extends BaseDaoImpl<CustomerAuthEntity> implements
    CustomerAuthDao {

  @Override
  public CustomerAuthEntity findToken(final String accessToken) {
    try {
      return entityManager.createNamedQuery(BY_ACCESS_TOKEN, CustomerAuthEntity.class)
          .setParameter("accessToken", accessToken).getSingleResult();
    } catch (NoResultException nre) {
      return null;
    }
  }

  @Override
  public CustomerAuthEntity findActiveTokenByUser(final long customerId,
      final ZonedDateTime currentAt) {
    try {
      return entityManager.createNamedQuery(BY_CUSTOMER, CustomerAuthEntity.class)
          .setParameter("customerId", customerId)
          .setParameter("currentAt", currentAt)
          .getSingleResult();
    } catch (NoResultException nre) {
      return null;
    }
  }

}