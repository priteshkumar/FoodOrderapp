package com.upgrad.FoodOrderingApp.api.controller.transformer;

import com.upgrad.FoodOrderingApp.api.model.ItemQuantity;
import com.upgrad.FoodOrderingApp.api.model.SaveOrderRequest;
import com.upgrad.FoodOrderingApp.service.common.DateTimeProvider;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.util.CollectionUtils;

public final class OrderTransformer {

  public static OrderEntity toEntity(final SaveOrderRequest saveOrderRequest,
      AddressEntity address, CustomerEntity customer, RestaurantEntity restaurant,
      PaymentEntity payment, CouponEntity coupon) {

    OrderEntity order = new OrderEntity();
    order.setUuid(UUID.randomUUID().toString());
    addItems(order, saveOrderRequest.getItemQuantities());
    order.setBill(saveOrderRequest.getBill().doubleValue());
    order.setDiscount(saveOrderRequest.getDiscount().doubleValue());
    order.setDate(DateTimeProvider.currentProgramTime());
    order.setAddress(address);
    order.setCustomer(customer);
    order.setRestaurant(restaurant);
    order.setPayment(payment);
    order.setCoupon(coupon);

    return order;
  }

  private static void addItems(final OrderEntity order, final List<ItemQuantity> items) {
    if (!CollectionUtils.isEmpty(items)) {
      items.forEach(itemQuantity -> {
        order.addItemUUID(itemQuantity.getItemId().toString());
      });
    }
  }
}
