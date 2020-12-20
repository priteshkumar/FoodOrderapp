package com.upgrad.FoodOrderingApp.service.dao;

import static com.upgrad.FoodOrderingApp.service.entity.OrderEntity.ORDERS_BY_ADDRESS;

import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity_;
import com.upgrad.FoodOrderingApp.service.entity.CategoryItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.CategoryItemEntity_;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity_;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity_;
import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderEntity_;
import com.upgrad.FoodOrderingApp.service.entity.OrderItemEntity;
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
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDaoImpl extends BaseDaoImpl<OrderEntity> implements OrderDao {

  @Override
  public List<OrderEntity> getOrdersByCustomers(@NotNull String customerId) {
    final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    final CriteriaQuery<OrderEntity> payloadQuery = builder
        .createQuery(OrderEntity.class);
    final Root<OrderEntity> from = payloadQuery.from(OrderEntity.class);
    final Predicate[] payloadPredicates = buildPredicates(customerId, builder,
        from);

    payloadQuery.select(from).where(payloadPredicates).distinct(true);
    payloadQuery
        .orderBy(builder.desc(from.get(OrderEntity_.date)));
    final List<OrderEntity> payload = entityManager.createQuery(payloadQuery)
        .getResultList();
    return payload;
  }

  @Override
  public boolean checkOrdersByAddress(@NotNull String addressId) {
    Long orderCount =
        (Long) entityManager.createNamedQuery(ORDERS_BY_ADDRESS)
            .setParameter("addressId", addressId).getSingleResult();
    return orderCount > 0 ? true : false;
  }

  private Predicate[] buildPredicates(final String customerId,
      final CriteriaBuilder builder, final Root<OrderEntity> root) {

    final List<Predicate> predicates = new ArrayList<>();
    if (!StringUtils.isEmpty(customerId)) {
      final ListJoin<OrderEntity, OrderItemEntity> orderItemJoin =
          root.join(OrderEntity_.orderItemEntityList);
      predicates.add(builder
          .equal(root.get(OrderEntity_.customer).get(CustomerEntity_.UUID), customerId));
    }
    return predicates.toArray(new Predicate[]{});
  }
}
