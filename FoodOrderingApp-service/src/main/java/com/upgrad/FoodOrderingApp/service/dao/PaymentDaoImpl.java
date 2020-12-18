package com.upgrad.FoodOrderingApp.service.dao;

import static com.upgrad.FoodOrderingApp.service.entity.PaymentEntity.BY_ALL_PAYMENTS;

import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentDaoImpl extends BaseDaoImpl<PaymentEntity> implements PaymentDao {

  @Override
  public List<PaymentEntity> getAllPaymentMethods() {
    return entityManager.createNamedQuery(BY_ALL_PAYMENTS, PaymentEntity.class).getResultList();
  }
}
