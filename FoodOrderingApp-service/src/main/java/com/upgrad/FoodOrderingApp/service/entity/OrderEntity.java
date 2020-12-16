package com.upgrad.FoodOrderingApp.service.entity;

import com.upgrad.FoodOrderingApp.service.entity.ext.EntityEqualsBuilder;
import com.upgrad.FoodOrderingApp.service.entity.ext.EntityHashCodeBuilder;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
/*
DROP TABLE IF EXISTS ORDERS CASCADE;

CREATE TABLE ORDERS(id SERIAL,
uuid VARCHAR(200) UNIQUE NOT NULL,
bill DECIMAL NOT NULL,
coupon_id INTEGER,
discount DECIMAL DEFAULT 0,
date TIMESTAMP NOT NULL ,
 payment_id INTEGER,
 customer_id INTEGER NOT NULL,
 address_id INTEGER NOT NULL,
 PRIMARY KEY(id),
 restaurant_id INTEGER NOT NULL ,
 FOREIGN KEY (payment_id) REFERENCES PAYMENT(id),
 FOREIGN KEY (restaurant_id) REFERENCES RESTAURANT(id),
 FOREIGN KEY (customer_id) REFERENCES CUSTOMER(id) ON DELETE CASCADE,
  FOREIGN KEY (address_id) REFERENCES ADDRESS(id),
  FOREIGN KEY (coupon_id) REFERENCES COUPON(id));
 */

@javax.persistence.Entity
@Table(name = "orders", schema = "public")
public class OrderEntity implements Entity, Identifier<Integer>, UniversalUniqueIdentifier<String>,
    Serializable {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "uuid", unique = true)
  @NotNull
  @Size(max = 200)
  private String uuid;

  @Column(name = "bill")
  @NotNull
  private Double bill;

  @Column(name = "discount", insertable = false)
  private Double discount;

  @Column(name = "\"date\"")
  @NotNull
  private ZonedDateTime date;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private CustomerEntity customer;

  @ManyToOne
  @JoinColumn(name = "address_id")
  private AddressEntity address;

  @ManyToOne
  @JoinColumn(name = "restaurant_id")
  private RestaurantEntity restaurant;

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

  public Double getBill() {
    return bill;
  }

  public void setBill(Double bill) {
    this.bill = bill;
  }

  public Double getDiscount() {
    return discount;
  }

  public void setDiscount(Double discount) {
    this.discount = discount;
  }

  public ZonedDateTime getDate() {
    return date;
  }

  public void setDate(ZonedDateTime date) {
    this.date = date;
  }

  public CustomerEntity getCustomer() {
    return customer;
  }

  public void setCustomer(CustomerEntity customer) {
    this.customer = customer;
  }

  public AddressEntity getAddress() {
    return address;
  }

  public void setAddress(AddressEntity address) {
    this.address = address;
  }

  public RestaurantEntity getRestaurant() {
    return restaurant;
  }

  public void setRestaurant(RestaurantEntity restaurant) {
    this.restaurant = restaurant;
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
