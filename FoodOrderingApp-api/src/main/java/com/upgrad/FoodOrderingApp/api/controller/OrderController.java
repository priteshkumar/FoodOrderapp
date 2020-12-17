package com.upgrad.FoodOrderingApp.api.controller;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.upgrad.FoodOrderingApp.api.controller.ext.ResponseBuilder;
import com.upgrad.FoodOrderingApp.api.controller.provider.BearerAuthDecoder;
import com.upgrad.FoodOrderingApp.api.controller.transformer.OrderTransformer;
import com.upgrad.FoodOrderingApp.api.model.CategoryDetailsResponse;
import com.upgrad.FoodOrderingApp.api.model.CouponDetailsResponse;
import com.upgrad.FoodOrderingApp.api.model.ItemQuantity;
import com.upgrad.FoodOrderingApp.api.model.SaveOrderRequest;
import com.upgrad.FoodOrderingApp.api.model.SaveOrderResponse;
import com.upgrad.FoodOrderingApp.service.businness.AddressService;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.businness.ItemService;
import com.upgrad.FoodOrderingApp.service.businness.OrderService;
import com.upgrad.FoodOrderingApp.service.businness.PaymentService;
import com.upgrad.FoodOrderingApp.service.businness.RestaurantService;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.ItemNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.PaymentMethodNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

  @Autowired
  private OrderService orderService;

  @Autowired
  private CustomerService customerService;

  @Autowired
  private PaymentService paymentService;

  @Autowired
  private AddressService addressService;

  @Autowired
  private RestaurantService restaurantService;

  @Autowired
  private ItemService itemService;

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

  @RequestMapping(method = POST, path = "/order", consumes = APPLICATION_JSON_UTF8_VALUE,
      produces = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<SaveOrderResponse> saveOrder(
      @RequestHeader final String authorization,
      @RequestBody final SaveOrderRequest saveOrderRequest)
      throws CouponNotFoundException, AuthorizationFailedException, RestaurantNotFoundException,
      AddressNotFoundException, PaymentMethodNotFoundException, ItemNotFoundException {

    BearerAuthDecoder bearerAuthDecoder = new BearerAuthDecoder(authorization);

    CustomerEntity customer = customerService.getCustomer(bearerAuthDecoder.getAccessToken());
    CouponEntity coupon =
        orderService.getCouponByCouponId(saveOrderRequest.getCouponId().toString());
    PaymentEntity payment = paymentService
        .getPaymentByUUID(saveOrderRequest.getPaymentId().toString());
    AddressEntity address = addressService
        .getAddressByUUID(saveOrderRequest.getAddressId(), customer);
    RestaurantEntity restaurant =
        restaurantService.restaurantByUUID(saveOrderRequest.getRestaurantId().toString());

    final OrderEntity order = OrderTransformer.toEntity(saveOrderRequest, address, customer,
        restaurant, payment, coupon);
    final OrderEntity createdOrder = orderService.saveOrder(order);
    final List<OrderItemEntity> orderItems = getOrderItems(saveOrderRequest, createdOrder);
    orderItems.forEach(orderItemEntity -> {
      orderService.saveOrderItem(orderItemEntity);
    });

    return ResponseBuilder.created().payload(
        new SaveOrderResponse().id(createdOrder.getUuid()).status("ORDER SUCCESSFULLY PLACED"))
        .build();
  }

  private CouponDetailsResponse toCouponDetailsResponse(CouponEntity coupon) {
    CouponDetailsResponse couponDetailsResponse =
        new CouponDetailsResponse().id(UUID.fromString(coupon.getUuid()))
            .couponName(coupon.getCouponName())
            .percent(coupon.getPercent());
    return couponDetailsResponse;
  }

  private List<OrderItemEntity> getOrderItems(final SaveOrderRequest saveOrderRequest,
      OrderEntity order) {
    List<ItemQuantity> itemQuantities = saveOrderRequest.getItemQuantities();
    List<OrderItemEntity> orderItems =
        Optional.ofNullable(itemQuantities).map(List::stream).orElse(Stream.empty())
            .map(itemQuantity -> {
              OrderItemEntity orderItem = new OrderItemEntity();
              orderItem.setPrice(itemQuantity.getPrice());
              orderItem.setQuantity(itemQuantity.getQuantity());
              try {
                orderItem.setItem(itemService.itemByUUID(itemQuantity.getItemId().toString()));
              } catch (ItemNotFoundException e) {
                e.printStackTrace();
              }
              orderItem.setOrder(order);
              return orderItem;
            }).collect(Collectors.toList());

    return orderItems;
  }
}
