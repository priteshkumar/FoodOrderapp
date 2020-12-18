package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.ItemNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import java.util.List;
import javax.validation.constraints.NotNull;

public interface ItemService {

  public List<ItemEntity> getItemsByPopularity(@NotNull RestaurantEntity restaurant);

  public ItemEntity itemByUUID(@NotNull String itemId) throws ItemNotFoundException;

  public List<ItemEntity> getItemsByCategoryAndRestaurant(@NotNull String restaurantId,
      @NotNull String categoryId);
}
