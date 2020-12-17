package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.ItemDao;
import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.ItemNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ItemServiceImpl implements ItemService {

  @Autowired
  private ItemDao itemDao;

  @Override
  public List<ItemEntity> getItemsByPopularity(RestaurantEntity restaurant) {

    return itemDao.getItemsByPopularity(restaurant.getUuid());
  }

  @Override
  public ItemEntity itemByUUID(@NotNull String itemId) throws ItemNotFoundException {
    ItemEntity item = itemDao.findByUUID(itemId);
    if (item == null) {
      throw new ItemNotFoundException("INF-003", "No item by this id exist");
    }
    return item;
  }
}
