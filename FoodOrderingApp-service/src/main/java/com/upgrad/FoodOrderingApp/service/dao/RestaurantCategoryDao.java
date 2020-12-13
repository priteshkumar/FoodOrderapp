package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import java.util.List;

public interface RestaurantCategoryDao extends BaseDao<CategoryEntity> {

  public List<CategoryEntity> getCategoriesByRestaurant(final String restaurantUUID);
}
