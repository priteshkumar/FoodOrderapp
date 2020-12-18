package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import com.upgrad.FoodOrderingApp.service.exception.PaymentMethodNotFoundException;
import java.util.List;
import javax.validation.constraints.NotNull;

public interface PaymentService {

  public PaymentEntity getPaymentByUUID(@NotNull String paymentId)
      throws PaymentMethodNotFoundException;

  public List<PaymentEntity> getAllPaymentMethods();
}
