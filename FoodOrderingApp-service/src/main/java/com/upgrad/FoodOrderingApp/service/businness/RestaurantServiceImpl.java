package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.common.SortBy;
import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RestaurantServiceImpl implements RestaurantService {

  @Autowired
  private RestaurantDao restaurantDao;

  @Override
  public List<RestaurantEntity> restaurantsByRating() {
    List<RestaurantEntity> restaurantEntities = restaurantDao
        .getAllRestaurants(null, SortBy.RESTAURANT_RATING);
    return restaurantEntities;
  }

  @Override
  public List<RestaurantEntity> restaurantsByName(final String restaurantName)
      throws RestaurantNotFoundException {
    if (StringUtils.isEmpty(restaurantName)) {
      throw new RestaurantNotFoundException("RNF-003",
          "Restaurant name field should not be empty");
    }
    return restaurantDao.getAllRestaurants(restaurantName, SortBy.RESTAURANT_NAME);
  }
}
