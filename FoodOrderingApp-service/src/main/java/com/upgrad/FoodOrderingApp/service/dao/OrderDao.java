package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderItemEntity;
import java.util.List;
import javax.validation.constraints.NotNull;

public interface OrderDao extends BaseDao<OrderEntity> {

  public List<OrderEntity> getOrdersByCustomers(@NotNull String customerId);
}
