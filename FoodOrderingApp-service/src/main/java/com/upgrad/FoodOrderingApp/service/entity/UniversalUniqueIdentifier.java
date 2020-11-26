package com.upgrad.FoodOrderingApp.service.entity;

/**
 * Interface that represents Universal unique identifier (UUID).
 */
public interface UniversalUniqueIdentifier<K> {

  /**
   * @return the type safe universal unique identifier.
   */
  K getUuid();

}