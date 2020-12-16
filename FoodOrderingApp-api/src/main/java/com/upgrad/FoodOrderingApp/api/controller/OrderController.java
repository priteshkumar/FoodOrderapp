package com.upgrad.FoodOrderingApp.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import com.upgrad.FoodOrderingApp.api.controller.ext.ResponseBuilder;
import com.upgrad.FoodOrderingApp.api.controller.provider.BearerAuthDecoder;
import com.upgrad.FoodOrderingApp.api.model.CategoryDetailsResponse;
import com.upgrad.FoodOrderingApp.api.model.CouponDetailsResponse;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.businness.OrderService;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

  @Autowired
  private OrderService orderService;

  @Autowired
  private CustomerService customerService;

  @RequestMapping(method = GET, path = "/order/coupon/{coupon_name}",
      produces = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<CouponDetailsResponse> getCategoryById(
      @RequestHeader final String authorization,
      @PathVariable("coupon_name") final String couponName)
      throws CouponNotFoundException, AuthorizationFailedException {

    BearerAuthDecoder bearerAuthDecoder = new BearerAuthDecoder(authorization);
    CustomerEntity customer = customerService.getCustomer(bearerAuthDecoder.getAccessToken());
    CouponEntity coupon = orderService.getCouponByCouponName(couponName);
    return ResponseBuilder.ok().payload(toCouponDetailsResponse(coupon))
        .build();
  }

  private CouponDetailsResponse toCouponDetailsResponse(CouponEntity coupon) {
    CouponDetailsResponse couponDetailsResponse =
        new CouponDetailsResponse().id(UUID.fromString(coupon.getUuid()))
            .couponName(coupon.getCouponName())
            .percent(coupon.getPercent());
    return couponDetailsResponse;
  }
}
