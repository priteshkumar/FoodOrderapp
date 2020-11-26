package com.upgrad.FoodOrderingApp.service.entity;

import java.time.ZonedDateTime;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

import com.upgrad.FoodOrderingApp.service.entity.Entity;
import com.upgrad.FoodOrderingApp.service.common.DateTimeProvider;

/**
 * Base class for all immutable entities to inherit the default behavior.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public class ImmutableEntity implements Entity {

  @Column(name = "CREATED_BY")
  @NotNull
  private String createdBy;

  @Column(name = "CREATED_AT")
  @NotNull
  private ZonedDateTime createdAt;

  @PrePersist
  public void prePersist() {
    this.createdBy = "api-backend";
    this.createdAt = DateTimeProvider.currentSystemTime();
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(ZonedDateTime createdAt) {
    this.createdAt = createdAt;
  }

}