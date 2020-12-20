package com.upgrad.FoodOrderingApp.service.entity;

import static com.upgrad.FoodOrderingApp.service.entity.PaymentEntity.BY_ALL_PAYMENTS;
import static com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity.BY_ALL_RESTAURANTS;
import static com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity.BY_RESTAURANT_UUID;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@javax.persistence.Entity
@Table(name = "payment", schema = "public")
@NamedQueries({
    @NamedQuery(name = BY_ALL_PAYMENTS, //
        query = "SELECT pay FROM PaymentEntity pay")
})
public class PaymentEntity implements Entity, Identifier<Integer>,
    UniversalUniqueIdentifier<String>, Serializable {

  public static final String BY_ALL_PAYMENTS = "PaymentEntity.byAllPayments";

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "uuid", unique = true)
  @NotNull
  @Size(max = 200)
  private String uuid;

  @Column(name = "payment_name")
  @Size(max = 255)
  private String paymentName;

  public PaymentEntity() {
  }

  public PaymentEntity(String uuid, String paymentName) {
    this.uuid = uuid;
    this.paymentName = paymentName;
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

  public String getPaymentName() {
    return paymentName;
  }

  public void setPaymentName(String paymentName) {
    this.paymentName = paymentName;
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
