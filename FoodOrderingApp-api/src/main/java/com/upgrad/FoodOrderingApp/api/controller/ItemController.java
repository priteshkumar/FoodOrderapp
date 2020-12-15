package com.upgrad.FoodOrderingApp.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import com.upgrad.FoodOrderingApp.api.controller.ext.ResponseBuilder;
import com.upgrad.FoodOrderingApp.api.model.CategoryDetailsResponse;
import com.upgrad.FoodOrderingApp.api.model.ItemList;
import com.upgrad.FoodOrderingApp.api.model.ItemList.ItemTypeEnum;
import com.upgrad.FoodOrderingApp.api.model.ItemListResponse;
import com.upgrad.FoodOrderingApp.service.businness.ItemService;
import com.upgrad.FoodOrderingApp.service.businness.RestaurantService;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
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
public class ItemController {

  @Autowired
  private RestaurantService restaurantService;

  @Autowired
  private ItemService itemService;

  @RequestMapping(method = GET, path = "/item/restaurant/{restaurant_id}",
      produces = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<ItemListResponse> getItemsByPopularity(
      @PathVariable("restaurant_id") final String restaurantId) throws RestaurantNotFoundException {

    RestaurantEntity restaurant = restaurantService.restaurantByUUID(restaurantId);
    List<ItemEntity> items = itemService.getItemsByPopularity(restaurant);
    return ResponseBuilder.ok().payload(toItemListResponse(items))
        .build();
  }

  private ItemListResponse toItemListResponse(List<ItemEntity> items) {
    ItemListResponse itemListResponse = new ItemListResponse();

    items.forEach(itemEntity -> {
      ItemList itemList = new ItemList().id(UUID.fromString(itemEntity.getUuid()))
          .itemName(itemEntity.getItemName()).price(itemEntity.getPrice()).itemType(
              ItemTypeEnum.fromValue(itemEntity.getType().equals("0") ? "VEG" : "NON_VEG"));
      itemListResponse.add(itemList);
    });
    return itemListResponse;
  }
}
