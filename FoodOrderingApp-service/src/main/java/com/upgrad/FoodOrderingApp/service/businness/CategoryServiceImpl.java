package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CategoryDao;
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
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CategoryServiceImpl implements CategoryService {

  @Autowired
  private CategoryDao categoryDao;

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

  @Override
  public CategoryEntity getCategoryById(String categoryId) throws CategoryNotFoundException {
    if (StringUtils.isEmpty(categoryId)) {
      throw new CategoryNotFoundException("CNF-001", "Category id field should not be empty");
    }
    CategoryEntity category = this.getCategory(categoryId);
    if (category == null) {
      throw new CategoryNotFoundException("CNF-002", "No category by this id");
    }
    return this.getAllCategoriesOrderedByName(categoryId).get(0);
  }

  @Override
  public List<CategoryEntity> getAllCategoriesOrderedByName() {
    return this.getAllCategoriesOrderedByName(null);
  }

  private List<CategoryEntity> getAllCategoriesOrderedByName(@Nullable String categoryId) {
    return categoryDao.getAllCategoriesOrderedByName(categoryId);
  }
}
