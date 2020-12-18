package com.upgrad.FoodOrderingApp.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import com.upgrad.FoodOrderingApp.api.controller.ext.ResponseBuilder;
import com.upgrad.FoodOrderingApp.api.model.CategoriesListResponse;
import com.upgrad.FoodOrderingApp.api.model.CategoryDetailsResponse;
import com.upgrad.FoodOrderingApp.api.model.CategoryListResponse;
import com.upgrad.FoodOrderingApp.api.model.ItemList;
import com.upgrad.FoodOrderingApp.api.model.ItemList.ItemTypeEnum;
import com.upgrad.FoodOrderingApp.api.model.RestaurantList;
import com.upgrad.FoodOrderingApp.api.model.RestaurantListResponse;
import com.upgrad.FoodOrderingApp.service.businness.CategoryService;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(exposedHeaders = {"access-token", "request-id", "location" }, maxAge = 3600,
    allowCredentials = "true")
@RestController
public class CategoryController {

  @Autowired
  private CategoryService categoryService;

  @RequestMapping(method = GET, path = "/category",
      produces = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<CategoriesListResponse> getAll() {

    List<CategoryEntity> categories = categoryService.getAllCategoriesOrderedByName();
    return ResponseBuilder.ok().payload(toCategoriesListResponse(categories))
        .build();
  }

  @RequestMapping(method = GET, path = "/category/{category_id}",
      produces = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<CategoryDetailsResponse> getCategoryById(
      @PathVariable("category_id") final String categoryId) throws CategoryNotFoundException {

    CategoryEntity category = categoryService.getCategoryById(categoryId);
    return ResponseBuilder.ok().payload(toCategoryDetailsResponse(category))
        .build();
  }

  private CategoriesListResponse toCategoriesListResponse(
      List<CategoryEntity> categoryEntities) {

    List<CategoryListResponse> categoriesList =
        Optional.ofNullable(categoryEntities).map(List::stream).orElseGet(Stream::empty)
            .map(categoryEntity -> {
              CategoryListResponse categoryListResponse =
                  new CategoryListResponse().id(UUID.fromString(categoryEntity.getUuid()))
                      .categoryName(categoryEntity.getCategoryName());
              return categoryListResponse;
            }).collect(Collectors.toList());
    return new CategoriesListResponse().categories(categoriesList);
  }

  private CategoryDetailsResponse toCategoryDetailsResponse(CategoryEntity categoryEntity) {
    CategoryDetailsResponse categoryDetailsResponse =
        new CategoryDetailsResponse().id(UUID.fromString(categoryEntity.getUuid()))
            .categoryName(categoryEntity.getCategoryName());
    List<ItemEntity> itemEntities = categoryEntity.getItems();
    List<ItemList> items =
        Optional.ofNullable(itemEntities).map(List::stream).orElseGet(Stream::empty)
            .map(itemEntity -> {
              ItemList itemList =
                  new ItemList().id(UUID.fromString(itemEntity.getUuid()))
                      .itemName(itemEntity.getItemName()).price(itemEntity.getPrice())
                      .itemType(ItemTypeEnum.fromValue(itemEntity.getType().equals("0") ? "VEG" :
                          "NON_VEG"));
              return itemList;
            }).collect(Collectors.toList());
    return categoryDetailsResponse.itemList(items);
  }
}
