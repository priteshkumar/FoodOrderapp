package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.InvalidRatingException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import java.util.List;
import javax.validation.constraints.NotNull;

public interface RestaurantService {

  public List<RestaurantEntity> restaurantsByRating();

  public List<RestaurantEntity> restaurantsByName(final String restaurantName)
      throws RestaurantNotFoundException;

  public List<RestaurantEntity> restaurantByCategory(final String categoryId)
      throws CategoryNotFoundException;

  public RestaurantEntity restaurantByUUID(final String restaurantId)
      throws RestaurantNotFoundException;

  public RestaurantEntity updateRestaurantRating(@NotNull RestaurantEntity restaurantEntity,
      final Double customerRating) throws InvalidRatingException;
}
