package com.upgrad.FoodOrderingApp.api.controller;

import static com.upgrad.FoodOrderingApp.api.controller.transformer.CustomerTransformer.toEntity;
import static com.upgrad.FoodOrderingApp.api.controller.transformer.CustomerTransformer.toSignupResponse;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.upgrad.FoodOrderingApp.api.controller.ext.ResponseBuilder;
import com.upgrad.FoodOrderingApp.api.controller.provider.BasicAuthDecoder;
import com.upgrad.FoodOrderingApp.api.model.LoginResponse;
import com.upgrad.FoodOrderingApp.api.model.SignupCustomerRequest;
import com.upgrad.FoodOrderingApp.api.model.SignupCustomerResponse;
import com.upgrad.FoodOrderingApp.service.businness.AuthenticationService;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import java.util.Base64;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

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

  @RequestMapping(method = POST, path = "/customer/login", consumes = APPLICATION_JSON_UTF8_VALUE,
      produces = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<LoginResponse> login(
      @RequestHeader final String authorization) throws AuthenticationFailedException {

    BasicAuthDecoder basicAuthDecoder = new BasicAuthDecoder(authorization);
    CustomerAuthEntity customerAuthEntity =
        customerService.authenticate(basicAuthDecoder.getUsername(),
            basicAuthDecoder.getPassword());
    return ResponseBuilder.ok().payload(toResponse(customerAuthEntity))
        .accessToken(customerAuthEntity.getAccessToken()).build();
  }

  private LoginResponse toResponse(CustomerAuthEntity customerAuthEntity) {
    CustomerEntity customerEntity = customerAuthEntity.getCustomer();
    LoginResponse loginResponse =
        new LoginResponse().id(customerEntity.getUuid())
            .firstName(customerEntity.getFirstName()).
            lastName(customerEntity.getLastName())
            .emailAddress(customerEntity.getEmail())
            .contactNumber(customerEntity.getContact_number())
            .message("LOGGED IN SUCCESSFULLY");
    return loginResponse;
  }
}