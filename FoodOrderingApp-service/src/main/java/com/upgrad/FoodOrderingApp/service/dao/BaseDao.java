package com.upgrad.FoodOrderingApp.service.dao;

/**
 * TODO: Provide javadoc
 */
public interface BaseDao<E> {

  E create(E e);

  E update(E e);

  E findById(final Object id);

  E findByUUID(final Object uuid);

  E delete(E e);
}