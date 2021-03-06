package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.common.SortBy;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import java.util.List;

public interface RestaurantDao extends BaseDao<RestaurantEntity> {

  public List<RestaurantEntity> getAllRestaurants(final String searchQuery, final SortBy sortBy,
      String categoryId);
}
