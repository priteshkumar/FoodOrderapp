package com.upgrad.FoodOrderingApp.service.dao;

import static com.upgrad.FoodOrderingApp.service.entity.CouponEntity.BY_COUPON_NAME;

import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import javax.persistence.NoResultException;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;

@Repository
public class CouponDaoImpl extends BaseDaoImpl<CouponEntity> implements CouponDao {

  @Override
  public CouponEntity getCouponByCouponName(@NotNull String couponName) {
    try {
      return entityManager.createNamedQuery(BY_COUPON_NAME, CouponEntity.class).setParameter(
          "couponName", couponName).getSingleResult();
    } catch (NoResultException nre) {
      return null;
    }
  }
}
