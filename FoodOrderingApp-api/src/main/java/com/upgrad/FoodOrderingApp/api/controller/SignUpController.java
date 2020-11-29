package com.upgrad.FoodOrderingApp.api.controller;

import static com.upgrad.FoodOrderingApp.api.controller.transformer.CustomerTransformer.toEntity;
import static com.upgrad.FoodOrderingApp.api.controller.transformer.CustomerTransformer.toSignupResponse;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.upgrad.FoodOrderingApp.api.controller.ext.ResponseBuilder;
import com.upgrad.FoodOrderingApp.api.model.SignupCustomerRequest;
import com.upgrad.FoodOrderingApp.api.model.SignupCustomerResponse;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignUpController {

  @Autowired
  private CustomerService customerService;

  @RequestMapping(method = POST, path = "/customer/signup", consumes = APPLICATION_JSON_UTF8_VALUE,
      produces = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<SignupCustomerResponse> signup(
      @RequestBody final SignupCustomerRequest signupCustomerRequest)
      throws SignUpRestrictedException {

    final CustomerEntity newCustomer = toEntity(signupCustomerRequest);
    final CustomerEntity registeredCustomer = customerService.saveCustomer(newCustomer);
    return ResponseBuilder.created().payload(toSignupResponse(registeredCustomer)).build();
  }

}