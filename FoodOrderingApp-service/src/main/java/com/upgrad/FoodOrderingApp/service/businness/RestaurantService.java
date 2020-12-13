package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import java.util.List;

public interface RestaurantService {

  public List<RestaurantEntity> restaurantsByRating();

  public List<RestaurantEntity> restaurantsByName(final String restaurantName)
      throws RestaurantNotFoundException;

  public List<RestaurantEntity> restaurantByCategory(final String categoryId)
      throws CategoryNotFoundException;

  public RestaurantEntity restaurantByUUID(final String restaurantId)
      throws RestaurantNotFoundException;
}
