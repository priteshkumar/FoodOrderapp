package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.PaymentDao;
import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import com.upgrad.FoodOrderingApp.service.exception.PaymentMethodNotFoundException;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

  @Autowired
  private PaymentDao paymentDao;

  @Override
  public PaymentEntity getPaymentByUUID(@NotNull String paymentId)
      throws PaymentMethodNotFoundException {
    PaymentEntity payment = paymentDao.findByUUID(paymentId);
    if (payment == null) {
      throw new PaymentMethodNotFoundException("PNF-002", "No payment method found by this id");
    }
    return payment;
  }

  @Override
  public List<PaymentEntity> getAllPaymentMethods() {
    return paymentDao.getAllPaymentMethods();
  }
}
