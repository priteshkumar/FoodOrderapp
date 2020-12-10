package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.RestaurantCategoryDao;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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

  @Override
  public CategoryEntity getCategory(String categoryId) {
    return restaurantCategoryDao.findByUUID(categoryId);
  }
}
