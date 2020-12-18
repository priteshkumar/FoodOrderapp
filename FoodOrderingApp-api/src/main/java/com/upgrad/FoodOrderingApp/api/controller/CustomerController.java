package com.upgrad.FoodOrderingApp.api.controller;

import static com.upgrad.FoodOrderingApp.api.controller.transformer.CustomerTransformer.toEntity;
import static com.upgrad.FoodOrderingApp.api.controller.transformer.CustomerTransformer.toSignupResponse;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import com.upgrad.FoodOrderingApp.api.controller.ext.ResponseBuilder;
import com.upgrad.FoodOrderingApp.api.controller.provider.BasicAuthDecoder;
import com.upgrad.FoodOrderingApp.api.controller.provider.BearerAuthDecoder;
import com.upgrad.FoodOrderingApp.api.model.LoginResponse;
import com.upgrad.FoodOrderingApp.api.model.LogoutResponse;
import com.upgrad.FoodOrderingApp.api.model.SignupCustomerRequest;
import com.upgrad.FoodOrderingApp.api.model.SignupCustomerResponse;
import com.upgrad.FoodOrderingApp.api.model.UpdateCustomerRequest;
import com.upgrad.FoodOrderingApp.api.model.UpdateCustomerResponse;
import com.upgrad.FoodOrderingApp.api.model.UpdatePasswordRequest;
import com.upgrad.FoodOrderingApp.api.model.UpdatePasswordResponse;
import com.upgrad.FoodOrderingApp.service.businness.AuthenticationService;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import com.upgrad.FoodOrderingApp.service.exception.UpdateCustomerException;
import java.util.Base64;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(exposedHeaders = {"access-token", "request-id", "location" }, maxAge = 3600,
    allowCredentials = "true")
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
    return ResponseBuilder.ok().payload(toLoginResponse(customerAuthEntity))
        .accessToken(customerAuthEntity.getAccessToken()).build();
  }

  @RequestMapping(method = POST, path = "/customer/logout", consumes = APPLICATION_JSON_UTF8_VALUE,
      produces = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<LogoutResponse> logout(
      @RequestHeader final String authorization) throws AuthorizationFailedException {

    BearerAuthDecoder bearerAuthDecoder = new BearerAuthDecoder(authorization);
    CustomerAuthEntity customerAuthEntity = customerService
        .logout(bearerAuthDecoder.getAccessToken());
    return ResponseBuilder.ok().payload(toLogoutResponse(customerAuthEntity)).build();
  }

  @RequestMapping(method = PUT, path = "/customer", consumes = APPLICATION_JSON_UTF8_VALUE,
      produces = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<UpdateCustomerResponse> update(
      @RequestHeader final String authorization,
      @RequestBody final UpdateCustomerRequest updateCustomerRequest)
      throws AuthorizationFailedException, UpdateCustomerException {

    CustomerEntity updatedCustomer = toEntity(updateCustomerRequest);
    BearerAuthDecoder bearerAuthDecoder = new BearerAuthDecoder(authorization);
    CustomerEntity customerEntity = customerService.getCustomer(bearerAuthDecoder.getAccessToken());
    CustomerEntity updatedCustomerEntity = updateCustomerData(customerEntity, updatedCustomer);
    updatedCustomerEntity = customerService.updateCustomer(updatedCustomerEntity);
    return ResponseBuilder.ok().payload(toUpdateResponse(customerEntity)).build();
  }

  @RequestMapping(method = PUT, path = "/customer/password", consumes = APPLICATION_JSON_UTF8_VALUE,
      produces = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<UpdatePasswordResponse> updatePassword(
      @RequestHeader final String authorization,
      @RequestBody final UpdatePasswordRequest updatePasswordRequest)
      throws AuthorizationFailedException, UpdateCustomerException {

    verifyPasswordData(updatePasswordRequest);
    BearerAuthDecoder bearerAuthDecoder = new BearerAuthDecoder(authorization);
    CustomerEntity customerEntity = customerService.getCustomer(bearerAuthDecoder.getAccessToken());
    CustomerEntity updatedCustomerEntity = customerService
        .updateCustomerPassword(updatePasswordRequest.getOldPassword(),
            updatePasswordRequest.getNewPassword(), customerEntity);
    return ResponseBuilder.ok().payload(toUpdatePasswordResponse(customerEntity)).build();
  }

  private LoginResponse toLoginResponse(CustomerAuthEntity customerAuthEntity) {
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

  private LogoutResponse toLogoutResponse(CustomerAuthEntity customerAuthEntity) {
    CustomerEntity customerEntity = customerAuthEntity.getCustomer();
    LogoutResponse logoutResponse =
        new LogoutResponse().id(customerEntity.getUuid())
            .message("LOGGED OUT SUCCESSFULLY");
    return logoutResponse;
  }

  private UpdateCustomerResponse toUpdateResponse(CustomerEntity customerEntity) {
    UpdateCustomerResponse updateCustomerResponse =
        new UpdateCustomerResponse().id(customerEntity.getUuid())
            .firstName(customerEntity.getFirstName())
            .lastName(customerEntity.getLastName())
            .status("CUSTOMER DETAILS UPDATED SUCCESSFULLY");
    return updateCustomerResponse;
  }

  private UpdatePasswordResponse toUpdatePasswordResponse(CustomerEntity customerEntity) {
    UpdatePasswordResponse updatePasswordResponse =
        new UpdatePasswordResponse().id(customerEntity.getUuid())
            .status("CUSTOMER PASSWORD UPDATED SUCCESSFULLY");
    return updatePasswordResponse;
  }

  private CustomerEntity updateCustomerData(CustomerEntity customerEntity,
      CustomerEntity updatedCustomer) {
    customerEntity.setFirstName(updatedCustomer.getFirstName());
    if (!StringUtils.isEmpty(updatedCustomer.getLastName())) {
      customerEntity.setLastName(updatedCustomer.getLastName());
    }
    return customerEntity;
  }

  private void verifyPasswordData(UpdatePasswordRequest updatePasswordRequest)
      throws UpdateCustomerException {
    if (StringUtils.isEmpty(updatePasswordRequest.getOldPassword()) || StringUtils
        .isEmpty(updatePasswordRequest.getNewPassword())) {
      throw new UpdateCustomerException("UCR-003", "No field should be empty");
    }
  }
}