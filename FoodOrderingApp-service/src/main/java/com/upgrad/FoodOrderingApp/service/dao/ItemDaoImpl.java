package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity_;
import com.upgrad.FoodOrderingApp.service.entity.CategoryItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.CategoryItemEntity_;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity_;
import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderEntity_;
import com.upgrad.FoodOrderingApp.service.entity.OrderItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderItemEntity_;
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
public class ItemDaoImpl extends BaseDaoImpl<ItemEntity> implements ItemDao {

  @Override
  public List<ItemEntity> getItemsByPopularity(String restaurantId) {

    final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    final CriteriaQuery<ItemEntity> payloadQuery = builder
        .createQuery(ItemEntity.class);
    final Root<ItemEntity> from = payloadQuery.from(ItemEntity.class);
    final Predicate[] payloadPredicates = buildPredicates(restaurantId, builder, from);
    payloadQuery.select(from).where(payloadPredicates);
    payloadQuery.groupBy(from.get(ItemEntity_.id))
        .orderBy(builder.desc(builder.count(from.get(ItemEntity_.id))));
    final List<ItemEntity> payload = entityManager.createQuery(payloadQuery).setMaxResults(5)
        .getResultList();
    return payload;
  }

  private Predicate[] buildPredicates(final String restaurantId, final CriteriaBuilder builder,
      final Root<ItemEntity> root) {

    final List<Predicate> predicates = new ArrayList<>();
    if (!StringUtils.isEmpty(restaurantId)) {
      final ListJoin<ItemEntity, OrderItemEntity> orderItemjoin =
          root.join(ItemEntity_.orderItemEntities);
      final Join<OrderItemEntity, OrderEntity> orderJoin =
          orderItemjoin.join(OrderItemEntity_.order);
      predicates.add(builder
          .equal(orderJoin.get(OrderEntity_.restaurant).get(RestaurantEntity_.UUID),
              restaurantId));
    }
    return predicates.toArray(new Predicate[]{});
  }
}
