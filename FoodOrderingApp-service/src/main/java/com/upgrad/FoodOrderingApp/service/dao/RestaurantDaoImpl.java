package com.upgrad.FoodOrderingApp.service.dao;

import static com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity.BY_ALL_RESTAURANTS;

import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity_;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

@Repository
public class RestaurantDaoImpl extends BaseDaoImpl<RestaurantEntity> implements RestaurantDao {

  @Override
  public List<RestaurantEntity> getAllRestaurants() {

    final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    final CriteriaQuery<RestaurantEntity> payloadQuery = builder
        .createQuery(RestaurantEntity.class);
    final Root<RestaurantEntity> from = payloadQuery.from(RestaurantEntity.class);
    //final Predicate[] payloadPredicates = buildPredicates(searchQuery, builder, from);
    payloadQuery.select(from);
    payloadQuery.orderBy(builder.desc(from.get(RestaurantEntity_.customerRating)));
    final List<RestaurantEntity> payload = entityManager.createQuery(payloadQuery).getResultList();
    return payload;
  }
}
