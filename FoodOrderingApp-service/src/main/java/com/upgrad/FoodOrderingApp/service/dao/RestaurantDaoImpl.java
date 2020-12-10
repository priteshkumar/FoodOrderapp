package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.common.SortBy;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity_;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategoryEntity_;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity_;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class RestaurantDaoImpl extends BaseDaoImpl<RestaurantEntity> implements RestaurantDao {

  @Override
  public List<RestaurantEntity> getAllRestaurants(final String searchQuery, final SortBy sortby,
      String categoryId) {

    final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    final CriteriaQuery<RestaurantEntity> payloadQuery = builder
        .createQuery(RestaurantEntity.class);
    final Root<RestaurantEntity> from = payloadQuery.from(RestaurantEntity.class);
    final Predicate[] payloadPredicates = buildPredicates(searchQuery, categoryId, builder, from);
    payloadQuery.select(from).where(payloadPredicates);

    if (SortBy.RESTAURANT_RATING == sortby) {
      payloadQuery.orderBy(builder.desc(from.get(RestaurantEntity_.customerRating)));
    } else {
      payloadQuery.orderBy(builder.asc(from.get(RestaurantEntity_.restaurantName)));
    }

    final List<RestaurantEntity> payload = entityManager.createQuery(payloadQuery).getResultList();
    return payload;
  }

  private Predicate[] buildPredicates(final String searchQuery, final String categoryId,
      final CriteriaBuilder builder, final Root<RestaurantEntity> root) {

    final List<Predicate> predicates = new ArrayList<>();

    if (!StringUtils.isEmpty(categoryId)) {
      final ListJoin<RestaurantEntity, RestaurantCategoryEntity> join =
          root.join(RestaurantEntity_.restaurantCategories);
      predicates.add(builder
          .equal(join.get(RestaurantCategoryEntity_.category).get(CategoryEntity_.UUID),
              categoryId));
    }
    if (StringUtils.isNotEmpty(searchQuery)) {
      predicates.add(builder.like(builder.lower(root.get(RestaurantEntity_.RESTAURANT_NAME)),
          like(searchQuery.toLowerCase())));
    }
    return predicates.toArray(new Predicate[]{});
  }
}