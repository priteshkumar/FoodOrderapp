package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CouponDao;
import com.upgrad.FoodOrderingApp.service.dao.ItemDao;
import com.upgrad.FoodOrderingApp.service.dao.OrderDao;
import com.upgrad.FoodOrderingApp.service.dao.OrderItemDao;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderItemEntity;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.ItemNotFoundException;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class OrderServiceImpl implements OrderService {

  @Autowired
  private CouponDao couponDao;

  @Autowired
  private ItemDao itemDao;

  @Autowired
  private OrderDao orderDao;

  @Autowired
  private OrderItemDao orderItemDao;

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

  @Override
  public CouponEntity getCouponByCouponId(@NotNull String couponId) throws CouponNotFoundException {
    CouponEntity coupon = couponDao.findByUUID(couponId);
    if (coupon == null) {
      throw new CouponNotFoundException("CPF-002", "No coupon by this id");
    }
    return coupon;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public OrderEntity saveOrder(@NotNull OrderEntity order) throws ItemNotFoundException {
    for (String itemUUID : order.getItemUuids()) {
      if (itemDao.findByUUID(itemUUID) == null) {
        throw new ItemNotFoundException("INF-003", "No item by this id exist");
      }
    }
    return orderDao.create(order);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public OrderItemEntity saveOrderItem(@NotNull OrderItemEntity orderItem) {
    return orderItemDao.create(orderItem);
  }

  @Override
  public List<OrderEntity> getOrdersByCustomers(@NotNull String customerId) {
    return orderDao.getOrdersByCustomers(customerId);
  }

  @Override
  public boolean checkOrdersByAddressExists(@NotNull String addressId) {
    return orderDao.checkOrdersByAddress(addressId);
  }
}
