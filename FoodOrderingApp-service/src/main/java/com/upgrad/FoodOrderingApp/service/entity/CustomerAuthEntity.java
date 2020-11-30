package com.upgrad.FoodOrderingApp.service.entity;

import static com.upgrad.FoodOrderingApp.service.entity.Entity.SCHEMA;
import com.upgrad.FoodOrderingApp.service.entity.ext.EntityEqualsBuilder;
import com.upgrad.FoodOrderingApp.service.entity.ext.EntityHashCodeBuilder;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import com.upgrad.FoodOrderingApp.service.entity.Entity;

/*
DROP TABLE IF EXISTS CUSTOMER_AUTH CASCADE;
    CREATE TABLE CUSTOMER_AUTH(
    id SERIAL,
    uuid VARCHAR(200) UNIQUE NOT NULL,
    customer_id INTEGER NOT NULL,
    access_token VARCHAR(500),
    login_at TIMESTAMP,
    logout_at TIMESTAMP,
    expires_at TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (customer_id) REFERENCES CUSTOMER(id) ON DELETE CASCADE);
*/

/**
 * Customer AuthToken entity JPA mapping class.
 */
@javax.persistence.Entity
@Table(name = "customer_auth", schema = SCHEMA)
@NamedQueries({ //

    @NamedQuery(name = CustomerAuthEntity.BY_ACCESS_TOKEN, //
        query = "SELECT uat FROM CustomerAuthEntity uat WHERE uat.accessToken = :accessToken"), //
    @NamedQuery(name = CustomerAuthEntity.BY_CUSTOMER, //
        query = "SELECT uat FROM CustomerAuthEntity uat WHERE uat.customer.id = :customerId AND"
            + " uat.logoutAt IS NULL AND uat.expiresAt > :currentAt"),
    //
})
public class CustomerAuthEntity implements Entity,
    Identifier<Integer>,
    UniversalUniqueIdentifier<String>,
    Serializable {

  public static final String BY_ACCESS_TOKEN = "CustomerAuthEntity.byAccessToken";
  public static final String BY_CUSTOMER = "CustomerAuthEntity.byCustomer";

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "uuid", unique = true)
  @NotNull
  @Size(max = 200)
  private String uuid;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private CustomerEntity customer;

  @Column(name = "access_token")
  @NotNull
  @Size(max = 500)
  private String accessToken;

  @Column(name = "login_at")
  @NotNull
  private ZonedDateTime loginAt;

  @Column(name = "expires_at")
  @NotNull
  private ZonedDateTime expiresAt;

  @Column(name = "logout_at")
  private ZonedDateTime logoutAt;

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

  public CustomerEntity getCustomer() {
    return customer;
  }

  public void setCustomer(CustomerEntity customer) {
    this.customer = customer;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public ZonedDateTime getLoginAt() {
    return loginAt;
  }

  public void setLoginAt(ZonedDateTime loginAt) {
    this.loginAt = loginAt;
  }

  public ZonedDateTime getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(ZonedDateTime expiresAt) {
    this.expiresAt = expiresAt;
  }

  public ZonedDateTime getLogoutAt() {
    return logoutAt;
  }

  public void setLogoutAt(ZonedDateTime logoutAt) {
    this.logoutAt = logoutAt;
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
