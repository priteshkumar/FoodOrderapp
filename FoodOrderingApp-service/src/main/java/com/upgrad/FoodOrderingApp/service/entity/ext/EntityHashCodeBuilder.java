package com.upgrad.FoodOrderingApp.service.entity.ext;

import com.upgrad.FoodOrderingApp.service.entity.Identifier;

/**
 * Builder class for the construction of hashCode for a particular entity.
 */
public final class EntityHashCodeBuilder<K> {

  public int hashCodeById(final Identifier<K> thisIdentifiableEntity) {
    if (thisIdentifiableEntity.getId() != null) {
      return thisIdentifiableEntity.getId().hashCode();
    } else {
      return System.identityHashCode(thisIdentifiableEntity);
    }
  }

}
