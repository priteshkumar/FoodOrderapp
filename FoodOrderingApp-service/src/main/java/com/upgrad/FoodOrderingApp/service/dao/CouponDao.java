package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import javax.validation.constraints.NotNull;

public interface CouponDao extends BaseDao<CouponEntity> {

  public CouponEntity getCouponByCouponName(@NotNull String couponName);
}
