package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import com.upgrad.FoodOrderingApp.service.exception.PaymentMethodNotFoundException;
import javax.validation.constraints.NotNull;

public interface PaymentService {

  public PaymentEntity getPaymentByUUID(@NotNull String paymentId)
      throws PaymentMethodNotFoundException;
}
