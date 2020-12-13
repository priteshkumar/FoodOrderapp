package com.upgrad.FoodOrderingApp.service.entity;

import com.upgrad.FoodOrderingApp.service.entity.ext.EntityEqualsBuilder;
import com.upgrad.FoodOrderingApp.service.entity.ext.EntityHashCodeBuilder;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@javax.persistence.Entity
@Table(name = "address", schema = "public")
public class AddressEntity implements Entity, Identifier<Integer>,
    UniversalUniqueIdentifier<String> {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "uuid", unique = true)
  @NotNull
  @Size(max = 200)
  private String uuid;

  @Column(name = "flat_buil_number")
  @Size(max = 255)
  private String flatBuilNo;

  @Column(name = "locality")
  @Size(max = 255)
  private String locality;

  @Column(name = "city")
  @Size(max = 30)
  private String city;

  @Column(name = "pincode")
  @Size(max = 30)
  private String pincode;

  @Column(name = "active",insertable = false)
  private int active;

  @ManyToOne
  @JoinColumn(name = "state_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private StateEntity state;

  public AddressEntity() {
  }

  public AddressEntity(String addressId, String s, String someLocality, String someCity, String s1,
      StateEntity stateEntity) {
    this.uuid = addressId;
    this.flatBuilNo = s;
    this.locality = someLocality;
    this.city = someCity;
    this.pincode = s1;
    this.state = stateEntity;
  }

  public String getFlatBuilNo() {
    return flatBuilNo;
  }

  public void setFlatBuilNo(String flatBuilNo) {
    this.flatBuilNo = flatBuilNo;
  }
  //setFlatBuilNo

  public String getLocality() {
    return locality;
  }

  public void setLocality(String locality) {
    this.locality = locality;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getPincode() {
    return pincode;
  }

  public void setPincode(String pincode) {
    this.pincode = pincode;
  }

  public int getActive() {
    return active;
  }

  public void setActive(int active) {
    this.active = active;
  }

  public StateEntity getState() {
    return state;
  }

  public void setState(StateEntity state) {
    this.state = state;
  }

  @Override
  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Override
  public String getUuid() {
    return this.uuid;
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
