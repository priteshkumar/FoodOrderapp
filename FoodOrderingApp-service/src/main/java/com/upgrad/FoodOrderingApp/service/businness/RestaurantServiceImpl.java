package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.common.SortBy;
import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.InvalidRatingException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class RestaurantServiceImpl implements RestaurantService {

  @Autowired
  private RestaurantDao restaurantDao;

  @Autowired
  private CategoryService categoryService;

  @Override
  public List<RestaurantEntity> restaurantsByRating() {
    List<RestaurantEntity> restaurantEntities = restaurantDao
        .getAllRestaurants(null, SortBy.RESTAURANT_RATING, null);
    return restaurantEntities;
  }

  @Override
  public List<RestaurantEntity> restaurantsByName(final String restaurantName)
      throws RestaurantNotFoundException {
    if (StringUtils.isEmpty(restaurantName)) {
      throw new RestaurantNotFoundException("RNF-003",
          "Restaurant name field should not be empty");
    }
    return restaurantDao.getAllRestaurants(restaurantName, SortBy.RESTAURANT_NAME, null);
  }

  @Override
  public List<RestaurantEntity> restaurantByCategory(String categoryId)
      throws CategoryNotFoundException {
    if (StringUtils.isEmpty(categoryId)) {
      throw new CategoryNotFoundException("CNF-001", "Category id field should not be empty");
    }
    CategoryEntity category = categoryService.getCategory(categoryId);
    if (category == null) {
      throw new CategoryNotFoundException("CNF-002", "No category by this id");
    }
    return restaurantDao.getAllRestaurants(null, SortBy.RESTAURANT_NAME, categoryId);
  }

  @Override
  public RestaurantEntity restaurantByUUID(String restaurantId) throws RestaurantNotFoundException {
    if (StringUtils.isEmpty(restaurantId)) {
      throw new RestaurantNotFoundException("RNF-002", "Restaurant id field should not be empty");
    }
    RestaurantEntity restaurantEntity = restaurantDao.findByUUID(restaurantId);
    if (restaurantEntity == null) {
      throw new RestaurantNotFoundException("RNF-001", "No restaurant by this id");
    }
    return restaurantEntity;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public RestaurantEntity updateRestaurantRating(@NotNull RestaurantEntity restaurantEntity,
      Double customerRating) throws InvalidRatingException {
    if (customerRating == null || customerRating < 1 || customerRating > 5) {
      throw new InvalidRatingException("IRE-001", "Restaurant should be in the range of 1 to 5");
    }
    restaurantEntity.setCustomerRating(customerRating);
    restaurantEntity.setNumberCustomersRated(restaurantEntity.getNumberCustomersRated() + 1);
    RestaurantEntity updatedRestaurant =  restaurantDao.update(restaurantEntity);
    return updatedRestaurant;
  }
}
