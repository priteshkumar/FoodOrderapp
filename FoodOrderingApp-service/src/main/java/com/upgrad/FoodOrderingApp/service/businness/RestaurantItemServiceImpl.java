package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.RestaurantItemDao;
import com.upgrad.FoodOrderingApp.service.dao.RestaurantItemDao;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantItemServiceImpl implements RestaurantItemService {

  @Autowired
  private RestaurantItemDao restaurantItemDao;

  @Override
  public List<ItemEntity> getItemsByCategoryAndRestaurant(String restaurantId,
      String categoryId) {
    return restaurantItemDao.getItemsByCategoryAndRestaurant(restaurantId, categoryId);
  }
}
