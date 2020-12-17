package com.upgrad.FoodOrderingApp.api.exception;

import com.upgrad.FoodOrderingApp.api.model.ErrorResponse;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.InvalidRatingException;
import com.upgrad.FoodOrderingApp.service.exception.ItemNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.PaymentMethodNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import com.upgrad.FoodOrderingApp.service.exception.UpdateCustomerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Global exception handler
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(SignUpRestrictedException.class)
  public ResponseEntity<ErrorResponse> signupRestrictedException(SignUpRestrictedException exe,
      WebRequest request) {
    return new ResponseEntity<ErrorResponse>(
        new ErrorResponse().code(exe.getCode()).message(exe.getErrorMessage()),
        HttpStatus.BAD_REQUEST
    );
  }

  @ExceptionHandler(AuthenticationFailedException.class)
  public ResponseEntity<ErrorResponse> authenticationException(AuthenticationFailedException exe,
      WebRequest request) {
    return new ResponseEntity<ErrorResponse>(
        new ErrorResponse().code(exe.getCode()).message(exe.getErrorMessage()),
        HttpStatus.UNAUTHORIZED
    );
  }

  @ExceptionHandler(AuthorizationFailedException.class)
  public ResponseEntity<ErrorResponse> authorizationException(AuthorizationFailedException exe,
      WebRequest request) {
    return new ResponseEntity<ErrorResponse>(
        new ErrorResponse().code(exe.getCode()).message(exe.getErrorMessage()),
        HttpStatus.FORBIDDEN
    );
  }

  @ExceptionHandler(UpdateCustomerException.class)
  public ResponseEntity<ErrorResponse> updateCustomerException(UpdateCustomerException exe,
      WebRequest request) {
    return new ResponseEntity<ErrorResponse>(
        new ErrorResponse().code(exe.getCode()).message(exe.getErrorMessage()),
        HttpStatus.BAD_REQUEST
    );
  }

  @ExceptionHandler(SaveAddressException.class)
  public ResponseEntity<ErrorResponse> saveAddressException(SaveAddressException exe,
      WebRequest request) {
    return new ResponseEntity<ErrorResponse>(
        new ErrorResponse().code(exe.getCode()).message(exe.getErrorMessage()),
        HttpStatus.BAD_REQUEST
    );
  }

  @ExceptionHandler(AddressNotFoundException.class)
  public ResponseEntity<ErrorResponse> addressException(AddressNotFoundException exe,
      WebRequest request) {
    return new ResponseEntity<ErrorResponse>(
        new ErrorResponse().code(exe.getCode()).message(exe.getErrorMessage()),
        HttpStatus.NOT_FOUND
    );
  }

  @ExceptionHandler(RestaurantNotFoundException.class)
  public ResponseEntity<ErrorResponse> restaurantException(RestaurantNotFoundException exe,
      WebRequest request) {
    return new ResponseEntity<ErrorResponse>(
        new ErrorResponse().code(exe.getCode()).message(exe.getErrorMessage()),
        HttpStatus.NOT_FOUND
    );
  }

  @ExceptionHandler(CategoryNotFoundException.class)
  public ResponseEntity<ErrorResponse> categoryException(CategoryNotFoundException exe,
      WebRequest request) {
    return new ResponseEntity<ErrorResponse>(
        new ErrorResponse().code(exe.getCode()).message(exe.getErrorMessage()),
        HttpStatus.NOT_FOUND
    );
  }

  @ExceptionHandler(InvalidRatingException.class)
  public ResponseEntity<ErrorResponse> invalidRatingexception(InvalidRatingException exe,
      WebRequest request) {
    return new ResponseEntity<ErrorResponse>(
        new ErrorResponse().code(exe.getCode()).message(exe.getErrorMessage()),
        HttpStatus.BAD_REQUEST
    );
  }

  @ExceptionHandler(CouponNotFoundException.class)
  public ResponseEntity<ErrorResponse> couponException(CouponNotFoundException exe,
      WebRequest request) {
    return new ResponseEntity<ErrorResponse>(
        new ErrorResponse().code(exe.getCode()).message(exe.getErrorMessage()),
        HttpStatus.NOT_FOUND
    );
  }

  @ExceptionHandler(PaymentMethodNotFoundException.class)
  public ResponseEntity<ErrorResponse> paymentException(PaymentMethodNotFoundException exe,
      WebRequest request) {
    return new ResponseEntity<ErrorResponse>(
        new ErrorResponse().code(exe.getCode()).message(exe.getErrorMessage()),
        HttpStatus.NOT_FOUND
    );
  }

  @ExceptionHandler(ItemNotFoundException.class)
  public ResponseEntity<ErrorResponse> itemException(ItemNotFoundException exe,
      WebRequest request) {
    return new ResponseEntity<ErrorResponse>(
        new ErrorResponse().code(exe.getCode()).message(exe.getErrorMessage()),
        HttpStatus.NOT_FOUND
    );
  }
}