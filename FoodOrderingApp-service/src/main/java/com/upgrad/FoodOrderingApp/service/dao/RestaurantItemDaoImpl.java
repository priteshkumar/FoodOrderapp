package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.common.SortBy;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity_;
import com.upgrad.FoodOrderingApp.service.entity.CategoryItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.CategoryItemEntity_;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity_;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategoryEntity_;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity_;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantItemEntity_;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class RestaurantItemDaoImpl extends BaseDaoImpl<RestaurantItemEntity> implements
    RestaurantItemDao {

  @Override
  public List<ItemEntity> getItemsByCategoryAndRestaurant(String restaurantId,
      String categoryId) {
    final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    final CriteriaQuery<RestaurantItemEntity> payloadQuery = builder
        .createQuery(RestaurantItemEntity.class);
    final Root<RestaurantItemEntity> from = payloadQuery.from(RestaurantItemEntity.class);
    final Predicate[] payloadPredicates = buildPredicates(restaurantId, categoryId, builder,
        from);
    payloadQuery.select(from).where(payloadPredicates);
    payloadQuery
        .orderBy(builder.asc(from.get(RestaurantItemEntity_.item).get(ItemEntity_.itemName)));
    final List<RestaurantItemEntity> payload = entityManager.createQuery(payloadQuery)
        .getResultList();
    List<ItemEntity> items =
        Optional.ofNullable(payload).map(List::stream).orElseGet(Stream::empty)
            .map(restaurantItemEntity -> {
              return restaurantItemEntity.getItem();
            }).collect(Collectors.toList());
    return items;
  }

  private Predicate[] buildPredicates(final String restaurantId, final String categoryId,
      final CriteriaBuilder builder, final Root<RestaurantItemEntity> root) {

    final List<Predicate> predicates = new ArrayList<>();

    if (!StringUtils.isEmpty(restaurantId)) {
      final Join<RestaurantItemEntity, ItemEntity> restItemjoin =
          root.join(RestaurantItemEntity_.item);
      predicates.add(builder
          .equal(root.get(RestaurantItemEntity_.restaurant).get(RestaurantEntity_.UUID),
              restaurantId));
      final ListJoin<ItemEntity, CategoryItemEntity> categoryItemJoin =
          restItemjoin.join(ItemEntity_.categoryItemEntities);
      predicates.add(builder
          .equal(categoryItemJoin.get(CategoryItemEntity_.category).get(CategoryEntity_.UUID),
              categoryId));
    }
    return predicates.toArray(new Predicate[]{});
  }
}
