package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import java.util.List;

public interface RestaurantItemService {

  public List<ItemEntity> getItemsByCategoryAndRestaurant(final String restaurantId,
      final String categoryId);
}
