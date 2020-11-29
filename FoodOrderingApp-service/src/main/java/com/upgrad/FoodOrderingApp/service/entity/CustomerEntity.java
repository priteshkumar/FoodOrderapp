package com.upgrad.FoodOrderingApp.service.entity;
/*CREATE TABLE CUSTOMER(id SERIAL,
    uuid VARCHAR(200) UNIQUE NOT NULL,
    firstname VARCHAR(30) NOT NULL,
    lastname VARCHAR(30) ,
    email VARCHAR(50),
    contact_number VARCHAR(30) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    salt VARCHAR(255) NOT NULL ,
     PRIMARY KEY(id));*/

import com.upgrad.FoodOrderingApp.service.entity.ext.EntityEqualsBuilder;
import com.upgrad.FoodOrderingApp.service.entity.ext.EntityHashCodeBuilder;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.apache.commons.lang3.builder.ToStringStyle;

@javax.persistence.Entity
@Table(name = "customer", schema = "public")
@NamedQueries({
    @NamedQuery(name = CustomerEntity.COUNT_BY_ALL, query = "select count(u.id) from "
        + "CustomerEntity u"),
    @NamedQuery(name = CustomerEntity.BY_ALL, query = "select u from CustomerEntity u"),
    @NamedQuery(name = CustomerEntity.BY_CONTACTNUMBER, query = "select u from CustomerEntity u "
        + "where u"
        + ".contact_number "
        + "= :contactNumber")
})

public class CustomerEntity implements Entity, Identifier<Integer>,
    UniversalUniqueIdentifier<String>,
    Serializable {

  public static final String COUNT_BY_ALL = "CustomerEntity.countByAll";
  public static final String BY_ALL = "CustomerEntity.byAll";
  public static final String BY_EMAIL = "CustomerEntity.byEmail";
  public static final String BY_CONTACTNUMBER = "CustomerEntity.byContactNumber";

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "uuid", unique = true)
  @NotNull
  @Size(max = 200)
  private String uuid;

  @Column(name = "firstname")
  @NotNull
  @Size(max = 30)
  private String firstName;

  @Column(name = "lastname")
  @Size(max = 30)
  private String lastName;

  @Column(name = "email")
  @Size(max = 50)
  private String email;

  @Column(name = "contact_number", unique = true)
  @NotNull
  @Size(max = 30)
  private String contact_number;

  @ToStringExclude
  @Column(name = "password")
  @NotNull
  @Size(max = 255)
  private String password;

  @Column(name = "salt")
  @NotNull
  @Size(max = 255)
  private String salt;

  @Override
  public Integer getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getContact_number() {
    return contact_number;
  }

  public void setContact_number(String contact_number) {
    this.contact_number = contact_number;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
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
