package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.Entity;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * Generic DAO abstraction for all DAOs to inherit common functionality.
 */
public class BaseDaoImpl<E extends Entity> implements BaseDao<E> {

  @PersistenceContext
  protected EntityManager entityManager;

  @Override
  public E create(E e) {
    entityManager.persist(e);
    return e;
  }

  @Override
  public E update(E e) {
    return entityManager.merge(e);
  }

  @Override
  @SuppressWarnings("unchecked")
  public E findById(final Object id) {
    final Class clazz = entityClass();
    return (E) entityManager.find(clazz, id);
  }

  @Override
  @SuppressWarnings("unchecked")
  public E findByUUID(final Object uuid) {
    try {
      final Class clazz = entityClass();
      return (E) entityManager.createQuery("SELECT e FROM " + clazz.getSimpleName()
          + " e WHERE e.uuid = :uuid", clazz).setParameter("uuid", uuid).getSingleResult();
    } catch (NoResultException exc) {
      return null;
    }
  }

  @Override
  public E delete(E e) {
    entityManager.remove(e);
    return e;
  }

  protected int getOffset(final int page, final int limit) {
    return (page - 1) * limit;
  }

  protected String like(final String text) {
    return "%" + text + "%";
  }

  private Class<?> entityClass() {

    final Type tType = ((ParameterizedType) this.getClass().getGenericSuperclass())
        .getActualTypeArguments()[0];
    try {
      return Class.forName(tType.getTypeName());
    } catch (ClassNotFoundException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

}