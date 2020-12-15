package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import java.util.List;

public interface ItemDao extends BaseDao<ItemEntity> {

  public List<ItemEntity> getItemsByPopularity(final String restaurantId);
}
