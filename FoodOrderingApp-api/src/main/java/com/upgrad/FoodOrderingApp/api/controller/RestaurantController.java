package com.upgrad.FoodOrderingApp.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import com.upgrad.FoodOrderingApp.api.controller.ext.ResponseBuilder;
import com.upgrad.FoodOrderingApp.api.model.CategoryList;
import com.upgrad.FoodOrderingApp.api.model.ItemList;
import com.upgrad.FoodOrderingApp.api.model.ItemList.ItemTypeEnum;
import com.upgrad.FoodOrderingApp.api.model.RestaurantDetailsResponse;
import com.upgrad.FoodOrderingApp.api.model.RestaurantDetailsResponseAddress;
import com.upgrad.FoodOrderingApp.api.model.RestaurantDetailsResponseAddressState;
import com.upgrad.FoodOrderingApp.api.model.RestaurantList;
import com.upgrad.FoodOrderingApp.api.model.RestaurantListResponse;
import com.upgrad.FoodOrderingApp.service.businness.CategoryService;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.businness.RestaurantItemService;
import com.upgrad.FoodOrderingApp.service.businness.RestaurantService;
import com.upgrad.FoodOrderingApp.service.common.ItemType;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
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

  @Autowired
  private RestaurantItemService itemService;

  @Autowired
  private CustomerService customerService;

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
      @PathVariable("restaurantName") final String restaurantName)
      throws RestaurantNotFoundException {
    final List<RestaurantEntity> restaurants = restaurantService.restaurantsByName(restaurantName);
    final List<RestaurantList> restaurantLists = prepareRestaurantList(restaurants);
    return ResponseBuilder.ok().payload(new RestaurantListResponse().restaurants(restaurantLists))
        .build();
  }

  @RequestMapping(method = GET, path = "/restaurant/category/{categoryId}",
      produces = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<RestaurantListResponse> getRestaurantsByCategory(
      @PathVariable("categoryId") final String categoryId)
      throws RestaurantNotFoundException, CategoryNotFoundException {

    final List<RestaurantEntity> restaurants = restaurantService.restaurantByCategory(categoryId);
    final List<RestaurantList> restaurantLists = prepareRestaurantList(restaurants);
    return ResponseBuilder.ok().payload(new RestaurantListResponse().restaurants(restaurantLists))
        .build();
  }


  @RequestMapping(method = GET, path = "/restaurant/{restaurantId}",
      produces = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<RestaurantDetailsResponse> getRestaurantById(
      @PathVariable("restaurantId") final String restaurantId)
      throws RestaurantNotFoundException {

    RestaurantEntity restaurant = restaurantService.restaurantByUUID(restaurantId);
    List<CategoryEntity> categories = categoryService.getCategoriesByRestaurant(restaurantId);

    List<CategoryList> categoryLists = prepareCategoryList(restaurantId, categories);
    return ResponseBuilder.ok().payload(toRestaurantDetailResponse(restaurant, categoryLists))
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

  private List<CategoryList> prepareCategoryList(final String restaurantId,
      List<CategoryEntity> categories) {
    List<CategoryList> categoryLists =
        Optional.ofNullable(categories).map(List::stream).orElseGet(Stream::empty)
            .map(categoryEntity -> {
              CategoryList categoryList = new CategoryList().id(
                  UUID.fromString(categoryEntity.getUuid()))
                  .categoryName(categoryEntity.getCategoryName());
              List<ItemEntity> items = itemService.getItemsByCategoryAndRestaurant(restaurantId,
                  categoryEntity.getUuid());

              List<ItemList> itemLists =
                  Optional.ofNullable(items).map(List::stream).orElseGet(Stream::empty)
                      .map(itemEntity -> {
                        ItemList itemList =
                            new ItemList().id(UUID.fromString(itemEntity.getUuid()))
                                .itemName(itemEntity.getItemName()).price(itemEntity.getPrice())
                                .itemType(
                                    ItemTypeEnum.fromValue(itemEntity.getType().equals("0") ?
                                        "VEG" :
                                        "NON_VEG"));
                        return itemList;
                      }).collect(Collectors.toList());
              return categoryList.itemList(itemLists);
            }).collect(Collectors.toList());
    return categoryLists;
  }

  private RestaurantDetailsResponse toRestaurantDetailResponse(RestaurantEntity restaurantEntity,
      List<CategoryList> categoryLists) {
    RestaurantDetailsResponse restaurantDetailsResponse =
        new RestaurantDetailsResponse().id(UUID.fromString(restaurantEntity.getUuid()))
            .restaurantName(restaurantEntity.getRestaurantName())
            .photoURL(restaurantEntity.getPhotoUrl()).averagePrice(restaurantEntity.getAvgPrice())
            .customerRating(
                BigDecimal.valueOf(restaurantEntity.getCustomerRating()))
            .numberCustomersRated(restaurantEntity.getNumberCustomersRated())
            .categories(categoryLists);

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
    return restaurantDetailsResponse.address(restaurantAddress);
  }
}