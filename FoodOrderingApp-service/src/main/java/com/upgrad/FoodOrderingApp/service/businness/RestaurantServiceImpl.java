package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantServiceImpl implements RestaurantService{

  @Autowired
  private RestaurantDao restaurantDao;

  @Override
  public List<RestaurantEntity> restaurantsByRating() {
    List<RestaurantEntity> restaurantEntities = restaurantDao.getAllRestaurants();
    return restaurantEntities;
  }
}
