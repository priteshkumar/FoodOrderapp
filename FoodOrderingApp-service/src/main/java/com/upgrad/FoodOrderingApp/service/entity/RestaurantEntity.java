package com.upgrad.FoodOrderingApp.service.entity;

import static com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity.BY_ALL_RESTAURANTS;
import static com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity.BY_RESTAURANT_UUID;
import static com.upgrad.FoodOrderingApp.service.entity.StateEntity.BY_ALL_STATES;
import static com.upgrad.FoodOrderingApp.service.entity.StateEntity.BY_STATE_UUID;

import com.upgrad.FoodOrderingApp.service.entity.ext.EntityEqualsBuilder;
import com.upgrad.FoodOrderingApp.service.entity.ext.EntityHashCodeBuilder;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@javax.persistence.Entity
@Table(name = "restaurant", schema = "public")
@NamedQueries({
    @NamedQuery(name = BY_RESTAURANT_UUID, //
        query = "SELECT rst FROM RestaurantEntity rst WHERE rst.uuid = :uuid"),
    @NamedQuery(name = BY_ALL_RESTAURANTS, //
        query = "SELECT rst FROM RestaurantEntity rst")
})
public class RestaurantEntity implements Entity, Identifier<Integer>,
    UniversalUniqueIdentifier<String>, Serializable {

  public final static String BY_RESTAURANT_UUID = "RestaurantEntity.byUUID";
  public final static String BY_ALL_RESTAURANTS = "RestaurantEntity.byAllRestaurants";

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "uuid", unique = true)
  @NotNull
  @Size(max = 200)
  private String uuid;

  @Column(name = "restaurant_name")
  @NotNull
  @Size(max = 50)
  private String restaurantName;

  @Column(name = "photo_url")
  @NotNull
  @Size(max = 255)
  private String photoUrl;

  @Column(name = "customer_rating")
  @NotNull
  private Double customerRating;

  @Column(name = "average_price_for_two")
  @NotNull
  private Integer avgPrice;

  @Column(name = "number_of_customers_rated", insertable = false)
  @NotNull
  private Integer numberCustomersRated;

  @OneToOne
  @JoinColumn(name = "address_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private AddressEntity address;

  @OneToMany
  @JoinColumn(name = "restaurant_id")
  private List<RestaurantCategoryEntity> restaurantCategories = new ArrayList<>();

  public List<RestaurantCategoryEntity> getRestaurantCategories() {
    return restaurantCategories;
  }

  public void setRestaurantCategories(
      List<RestaurantCategoryEntity> restaurantCategories) {
    this.restaurantCategories = restaurantCategories;
  }

  public String getRestaurantName() {
    return restaurantName;
  }

  public void setRestaurantName(String restaurantName) {
    this.restaurantName = restaurantName;
  }

  public String getPhotoUrl() {
    return photoUrl;
  }

  public void setPhotoUrl(String photoUrl) {
    this.photoUrl = photoUrl;
  }

  public Double getCustomerRating() {
    return customerRating;
  }

  public void setCustomerRating(Double customerRating) {
    this.customerRating = customerRating;
  }

  public Integer getAvgPrice() {
    return avgPrice;
  }

  public void setAvgPrice(Integer avgPrice) {
    this.avgPrice = avgPrice;
  }

  public Integer getNumberCustomersRated() {
    return numberCustomersRated;
  }

  public void setNumberCustomersRated(Integer numberCustomersRated) {
    this.numberCustomersRated = numberCustomersRated;
  }

  public AddressEntity getAddress() {
    return address;
  }

  public void setAddress(AddressEntity address) {
    this.address = address;
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
