package com.upgrad.FoodOrderingApp.service.entity;

/**
 * Interface that represents Identifiable.
 */
public interface Identifier<K> {

  /**
   * @return the type safe identifiable object.
   */
  K getId();

}