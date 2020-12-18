package com.upgrad.FoodOrderingApp.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import com.upgrad.FoodOrderingApp.api.controller.ext.ResponseBuilder;
import com.upgrad.FoodOrderingApp.api.model.CategoriesListResponse;
import com.upgrad.FoodOrderingApp.api.model.PaymentListResponse;
import com.upgrad.FoodOrderingApp.api.model.PaymentResponse;
import com.upgrad.FoodOrderingApp.service.businness.PaymentService;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(exposedHeaders = {"access-token", "request-id",
    "location" }, maxAge = 3600, allowCredentials = "true")
@RestController
public class PaymentController {

  @Autowired
  private PaymentService paymentService;

  @RequestMapping(method = GET, path = "/payment",
      produces = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<CategoriesListResponse> getAll() {

    List<PaymentEntity> payments = paymentService.getAllPaymentMethods();
    return ResponseBuilder.ok().payload(toPaymentListResponse(payments))
        .build();
  }

  private PaymentListResponse toPaymentListResponse(List<PaymentEntity> payments) {
    PaymentListResponse paymentListResponse = new PaymentListResponse();
    List<PaymentResponse> paymentResponses =
        Optional.ofNullable(payments).map(List::stream).orElseGet(Stream::empty)
            .map(paymentEntity -> {
              PaymentResponse paymentResponse =
                  new PaymentResponse().id(UUID.fromString(paymentEntity.getUuid()))
                      .paymentName(paymentEntity.getPaymentName());
              return paymentResponse;
            }).collect(Collectors.toList());

    return paymentListResponse.paymentMethods(paymentResponses);
  }
}
