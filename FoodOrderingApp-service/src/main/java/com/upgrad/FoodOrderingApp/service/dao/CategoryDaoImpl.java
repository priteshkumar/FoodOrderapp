package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.common.SortBy;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity_;
import com.upgrad.FoodOrderingApp.service.entity.CategoryItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.CategoryItemEntity_;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategoryEntity_;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity_;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDaoImpl extends BaseDaoImpl<CategoryEntity> implements CategoryDao {

  @Override
  public List<CategoryEntity> getAllCategoriesOrderedByName(final String categoryId) {

    final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    final CriteriaQuery<CategoryEntity> payloadQuery = builder
        .createQuery(CategoryEntity.class);
    final Root<CategoryEntity> from = payloadQuery.from(CategoryEntity.class);
    final Predicate[] payloadPredicates = buildPredicates(categoryId, builder, from);
    payloadQuery.select(from).where(payloadPredicates);
    payloadQuery.orderBy(builder.asc(from.get(CategoryEntity_.categoryName)));
    final List<CategoryEntity> payload = entityManager.createQuery(payloadQuery).getResultList();
    return payload;
  }

  private Predicate[] buildPredicates(final String categoryId,
      final CriteriaBuilder builder, final Root<CategoryEntity> root) {

    final List<Predicate> predicates = new ArrayList<>();
    if (!StringUtils.isEmpty(categoryId)) {
      predicates.add(builder.equal(root.get(CategoryEntity_.UUID),categoryId));
      final ListJoin<CategoryEntity, CategoryItemEntity> join =
          root.join(CategoryEntity_.categoryItemEntities);
      predicates.add(builder
          .equal(join.get(CategoryItemEntity_.category).get(CategoryEntity_.UUID),
              categoryId));
    }

    return predicates.toArray(new Predicate[]{});
  }
}
