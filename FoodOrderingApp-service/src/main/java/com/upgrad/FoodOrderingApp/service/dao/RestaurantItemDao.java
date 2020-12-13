package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantItemEntity;
import java.util.List;

public interface RestaurantItemDao extends BaseDao<RestaurantItemEntity> {

  public List<ItemEntity> getItemsByCategoryAndRestaurant(final String restaurantId,
      final String categoryId);
}
