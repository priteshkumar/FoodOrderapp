package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import java.util.List;

public interface CategoryService {

  public List<CategoryEntity> getCategoriesByRestaurant(final String uuid);
}
