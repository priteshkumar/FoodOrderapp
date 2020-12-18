package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import java.util.List;

public interface PaymentDao extends BaseDao<PaymentEntity> {

  public List<PaymentEntity> getAllPaymentMethods();
}
