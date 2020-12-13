package com.upgrad.FoodOrderingApp.service.entity;

import com.upgrad.FoodOrderingApp.service.entity.ext.EntityEqualsBuilder;
import com.upgrad.FoodOrderingApp.service.entity.ext.EntityHashCodeBuilder;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@javax.persistence.Entity
@Table(name = "category_item", schema = "public")
public class CategoryItemEntity implements Entity, Identifier<Integer>, Serializable {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "category_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private CategoryEntity category;

  @ManyToOne
  @JoinColumn(name = "item_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private ItemEntity item;

  public CategoryEntity getCategory() {
    return category;
  }

  public void setCategory(CategoryEntity category) {
    this.category = category;
  }

  public ItemEntity getItem() {
    return item;
  }

  public void setItem(ItemEntity item) {
    this.item = item;
  }

  @Override
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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
