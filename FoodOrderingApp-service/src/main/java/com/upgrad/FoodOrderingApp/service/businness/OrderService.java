package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import javax.validation.constraints.NotNull;

public interface OrderService {

  public CouponEntity getCouponByCouponName(@NotNull String couponName)
      throws CouponNotFoundException;
}
