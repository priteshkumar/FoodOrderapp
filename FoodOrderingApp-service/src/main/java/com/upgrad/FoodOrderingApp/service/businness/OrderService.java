package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderItemEntity;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.ItemNotFoundException;
import javax.validation.constraints.NotNull;

public interface OrderService {

  public CouponEntity getCouponByCouponName(@NotNull String couponName)
      throws CouponNotFoundException;

  public CouponEntity getCouponByCouponId(@NotNull String couponId) throws CouponNotFoundException;

  public OrderEntity saveOrder(@NotNull OrderEntity order) throws ItemNotFoundException;

  public OrderItemEntity saveOrderItem(@NotNull OrderItemEntity orderItem);
}
