package com.upgrad.FoodOrderingApp.service.entity;

import com.upgrad.FoodOrderingApp.service.entity.ext.EntityEqualsBuilder;
import com.upgrad.FoodOrderingApp.service.entity.ext.EntityHashCodeBuilder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import javax.persistence.CascadeType;
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
@Table(name = "category", schema = "public")
public class CategoryEntity implements Entity, Identifier<Integer>,
    UniversalUniqueIdentifier<String>, Serializable {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "uuid", unique = true)
  @NotNull
  @Size(max = 200)
  private String uuid;

  @Column(name = "category_name")
  @Size(max = 255)
  private String categoryName;

  @OneToMany(cascade = {CascadeType.ALL})
  @JoinColumn(name = "category_id")
  private List<CategoryItemEntity> categoryItemEntities = new ArrayList<>();

  public List<CategoryItemEntity> getCategoryItemEntities() {
    return categoryItemEntities;
  }

  public void setCategoryItemEntities(
      List<CategoryItemEntity> categoryItemEntities) {
    this.categoryItemEntities = categoryItemEntities;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
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

  public void setItems(List<ItemEntity> items){
    this.categoryItemEntities.clear();
    items.forEach(itemEntity -> {
      CategoryItemEntity categoryItem = new CategoryItemEntity();
      categoryItem.setCategory(this);
      categoryItem.setItem(itemEntity);
      this.categoryItemEntities.add(categoryItem);
    });
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
