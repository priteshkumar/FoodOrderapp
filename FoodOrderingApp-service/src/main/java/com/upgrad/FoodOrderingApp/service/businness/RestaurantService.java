package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import java.util.List;

public interface RestaurantService {

  public List<RestaurantEntity> restaurantsByRating();

}
