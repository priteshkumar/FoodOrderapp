package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.RestaurantCategoryDao;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

  @Autowired
  private RestaurantCategoryDao restaurantCategoryDao;

  @Override
  public List<CategoryEntity> getCategoriesByRestaurant(String uuid) {
    return restaurantCategoryDao.getCategoriesByRestaurant(uuid);
  }
}
