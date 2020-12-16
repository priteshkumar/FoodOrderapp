package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CouponDao;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class OrderServiceImpl implements OrderService {

  @Autowired
  private CouponDao couponDao;

  @Override
  public CouponEntity getCouponByCouponName(@NotNull String couponName)
      throws CouponNotFoundException {

    if (StringUtils.isEmpty(couponName)) {
      throw new CouponNotFoundException("CPF-002", "Coupon name field should not be empty");
    }
    CouponEntity coupon = couponDao.getCouponByCouponName(couponName);
    if (coupon == null) {
      throw new CouponNotFoundException("CPF-001", "No coupon by this name");
    }
    return coupon;
  }
}
