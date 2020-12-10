package com.upgrad.FoodOrderingApp.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import com.upgrad.FoodOrderingApp.api.controller.ext.ResponseBuilder;
import com.upgrad.FoodOrderingApp.api.model.RestaurantDetailsResponseAddress;
import com.upgrad.FoodOrderingApp.api.model.RestaurantDetailsResponseAddressState;
import com.upgrad.FoodOrderingApp.api.model.RestaurantList;
import com.upgrad.FoodOrderingApp.api.model.RestaurantListResponse;
import com.upgrad.FoodOrderingApp.api.model.StatesListResponse;
import com.upgrad.FoodOrderingApp.service.businness.CategoryService;
import com.upgrad.FoodOrderingApp.service.businness.RestaurantService;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestaurantController {

  @Autowired
  private RestaurantService restaurantService;

  @Autowired
  private CategoryService categoryService;

  @RequestMapping(method = GET, path = "/restaurant",
      produces = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<RestaurantListResponse> getAll() {

    List<RestaurantEntity> restaurants = restaurantService.restaurantsByRating();
    final List<RestaurantList> restaurantLists = prepareRestaurantList(restaurants);
    return ResponseBuilder.ok().payload(new RestaurantListResponse().restaurants(restaurantLists))
        .build();
  }

  @RequestMapping(method = GET, path = "/restaurant/name/{restaurantName}",
      produces = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<RestaurantListResponse> getRestaurantsByName(
      @PathVariable(value = "restaurantName",required = false) final String restaurantName)
      throws RestaurantNotFoundException {
    final List<RestaurantEntity> restaurants = restaurantService.restaurantsByName(restaurantName);
    final List<RestaurantList> restaurantLists = prepareRestaurantList(restaurants);
    return ResponseBuilder.ok().payload(new RestaurantListResponse().restaurants(restaurantLists))
        .build();
  }

  private List<RestaurantList> prepareRestaurantList(List<RestaurantEntity> restaurants) {
    List<RestaurantList> restaurantLists =
        Optional.ofNullable(restaurants).map(List::stream).orElseGet(Stream::empty)
            .map(restaurantEntity -> {
              RestaurantList restaurantList = new RestaurantList().id(
                  UUID.fromString(restaurantEntity.getUuid()))
                  .restaurantName(restaurantEntity.getRestaurantName())
                  .customerRating(BigDecimal.valueOf(restaurantEntity.getCustomerRating()))
                  .photoURL(restaurantEntity.getPhotoUrl())
                  .averagePrice(restaurantEntity.getAvgPrice())
                  .numberCustomersRated(restaurantEntity.getNumberCustomersRated());

              RestaurantDetailsResponseAddress restaurantAddress =
                  new RestaurantDetailsResponseAddress().id(
                      UUID.fromString(restaurantEntity.getAddress().getUuid()))
                      .flatBuildingName(restaurantEntity.getAddress().getFlatBuilNo())
                      .locality(restaurantEntity.getAddress().getLocality())
                      .pincode(restaurantEntity.getAddress().getPincode())
                      .city(restaurantEntity.getAddress().getCity());

              RestaurantDetailsResponseAddressState addressState =
                  new RestaurantDetailsResponseAddressState().id(
                      UUID.fromString(restaurantEntity.getAddress().getState().getUuid()))
                      .stateName(restaurantEntity.getAddress().getState().getState_name());

              restaurantAddress.setState(addressState);
              restaurantList.setAddress(restaurantAddress);

              List<CategoryEntity> categories =
                  categoryService.getCategoriesByRestaurant(restaurantEntity.getUuid());

              StringBuilder res = new StringBuilder();
              for (int i = 0; i < categories.size() - 1; i++) {
                res = res.append(categories.get(i).getCategoryName()).append(", ");
              }
              res = res.append(categories.get(categories.size() - 1).getCategoryName());
              restaurantList.setCategories(res.toString());
              return restaurantList;
            }).collect(Collectors.toList());

    return restaurantLists;
  }
}