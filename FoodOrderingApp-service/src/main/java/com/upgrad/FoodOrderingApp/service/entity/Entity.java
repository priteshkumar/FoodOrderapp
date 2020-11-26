package com.upgrad.FoodOrderingApp.service.entity;

/**
 * Interface that need to be implemented by the entity classes.
 */
public interface Entity {

  String SCHEMA = "public";

  @Override
  boolean equals(Object that);

  @Override
  int hashCode();

  @Override
  String toString();

}