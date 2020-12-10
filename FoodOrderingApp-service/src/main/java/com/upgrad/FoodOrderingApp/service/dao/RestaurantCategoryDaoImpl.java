package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity_;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategoryEntity_;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity_;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

@Repository
public class RestaurantCategoryDaoImpl extends BaseDaoImpl<CategoryEntity> implements RestaurantCategoryDao {

  @Override
  public List<CategoryEntity> getCategoriesByRestaurant(String restaurantUUID) {
    final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    final CriteriaQuery<RestaurantCategoryEntity> payloadQuery = builder
        .createQuery(RestaurantCategoryEntity.class);
    final Root<RestaurantCategoryEntity> from = payloadQuery.from(RestaurantCategoryEntity.class);
    Join<RestaurantCategoryEntity, CategoryEntity> categories =
        from.join(RestaurantCategoryEntity_.category);
    payloadQuery.select(from)
        .where(builder
            .equal(from.get(RestaurantCategoryEntity_.restaurant).get(RestaurantEntity_.UUID),
                restaurantUUID));
    payloadQuery.orderBy(builder
        .asc(from.get(RestaurantCategoryEntity_.category).get(CategoryEntity_.categoryName)));
    final List<RestaurantCategoryEntity> payload =
        entityManager.createQuery(payloadQuery).getResultList();

    List<CategoryEntity> categoryEntities =
        Optional.ofNullable(payload).map(List::stream).orElseGet(Stream::empty)
            .map(RestaurantCategoryEntity::getCategory).collect(Collectors.toList());
    return categoryEntities;
  }
}
