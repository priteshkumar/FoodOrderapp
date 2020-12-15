package com.upgrad.FoodOrderingApp.service.entity;

import com.upgrad.FoodOrderingApp.service.entity.ext.EntityEqualsBuilder;
import com.upgrad.FoodOrderingApp.service.entity.ext.EntityHashCodeBuilder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@javax.persistence.Entity
@Table(name = "item", schema = "public")
public class ItemEntity implements Entity, Identifier<Integer>, UniversalUniqueIdentifier<String>,
    Serializable {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "uuid", unique = true)
  @NotNull
  @Size(max = 200)
  private String uuid;

  @Column(name = "item_name")
  @NotNull
  @Size(max = 30)
  private String itemName;

  @Column(name = "price")
  @NotNull
  private Integer price;

  @Column(name = "type")
  @NotNull
  @Size(max = 10)
  private String type;

  @OneToMany
  @JoinColumn(name = "item_id")
  private List<RestaurantItemEntity> restaurantItems = new ArrayList<>();

  @OneToMany
  @JoinColumn(name = "item_id")
  private List<CategoryItemEntity> categoryItemEntities = new ArrayList<>();

  @OneToMany
  @JoinColumn(name = "item_id")
  private List<OrderItemEntity> orderItemEntities = new ArrayList<>();

  public List<OrderItemEntity> getOrderItemEntities() {
    return orderItemEntities;
  }

  public void setOrderItemEntities(List<OrderItemEntity> orderItemEntities) {
    this.orderItemEntities = orderItemEntities;
  }

  public List<RestaurantItemEntity> getRestaurantItems() {
    return restaurantItems;
  }

  public void setRestaurantItems(
      List<RestaurantItemEntity> restaurantItems) {
    this.restaurantItems = restaurantItems;
  }

  public List<CategoryItemEntity> getCategoryItemEntities() {
    return categoryItemEntities;
  }

  public void setCategoryItemEntities(
      List<CategoryItemEntity> categoryItemEntities) {
    this.categoryItemEntities = categoryItemEntities;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Override
  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  @Override
  public boolean equals(Object obj) {
    return new EntityEqualsBuilder<Integer>().equalsById(this, obj);
  }

  @Override
  public int hashCode() {
    return new EntityHashCodeBuilder<Integer>().hashCodeById(this);
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
}
