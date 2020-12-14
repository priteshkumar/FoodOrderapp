package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.lang.Nullable;

public interface CategoryService {

  public List<CategoryEntity> getCategoriesByRestaurant(final String uuid);

  public CategoryEntity getCategory(final String categoryId);

  public CategoryEntity getCategoryById(final String categoryId) throws CategoryNotFoundException;

  public List<CategoryEntity> getAllCategoriesOrderedByName();
}
