package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import java.util.List;

public interface CategoryDao extends BaseDao<CategoryEntity> {

  public List<CategoryEntity> getAllCategoriesOrderedByName(final String categoryId);
}
