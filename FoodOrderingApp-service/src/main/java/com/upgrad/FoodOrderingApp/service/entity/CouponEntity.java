package com.upgrad.FoodOrderingApp.service.entity;

import static com.upgrad.FoodOrderingApp.service.entity.CouponEntity.BY_COUPON_NAME;

import com.upgrad.FoodOrderingApp.service.entity.ext.EntityEqualsBuilder;
import com.upgrad.FoodOrderingApp.service.entity.ext.EntityHashCodeBuilder;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@javax.persistence.Entity
@Table(name = "coupon", schema = "public")
@NamedQueries({
    @NamedQuery(name = BY_COUPON_NAME, query = "select cou from CouponEntity cou where cou"
        + ".couponName = :couponName")
})

public class CouponEntity implements Entity, Identifier<Integer>, UniversalUniqueIdentifier<String>
    , Serializable {

  public static final String BY_COUPON_NAME = "CouponEntity.byCouponName";

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "uuid", unique = true)
  @NotNull
  @Size(max = 200)
  private String uuid;

  @Column(name = "coupon_name")
  @Size(max = 255)
  private String couponName;

  @Column(name = "percent")
  @NotNull
  private Integer percent;

  public CouponEntity() {
  }

  public CouponEntity(String uuid, String name, Integer percent) {
    this.uuid = uuid;
    this.couponName = name;
    this.percent = percent;
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

  public String getCouponName() {
    return couponName;
  }

  public void setCouponName(String couponName) {
    this.couponName = couponName;
  }

  public Integer getPercent() {
    return percent;
  }

  public void setPercent(Integer percent) {
    this.percent = percent;
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
