package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.common.CustomerAuthStatus;
import com.upgrad.FoodOrderingApp.service.common.DateTimeProvider;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import java.time.ZonedDateTime;

/**
 * Verifies the authentication token and capture the status.
 */
public final class CustomerAuthVerifier {

  private final CustomerAuthStatus status;

  public CustomerAuthVerifier(final CustomerAuthEntity customerAuthEntity) {

    if (customerAuthEntity == null) {
      status = CustomerAuthStatus.NOT_FOUND;
    } else if (isLoggedOut(customerAuthEntity)) {
      status = CustomerAuthStatus.LOGGED_OUT;
    } else if (isExpired(customerAuthEntity)) {
      status = CustomerAuthStatus.EXPIRED;
    } else {
      status = CustomerAuthStatus.ACTIVE;
    }
  }

  public boolean isActive() {
    return CustomerAuthStatus.ACTIVE == status;
  }

  public boolean hasExpired() {
    return CustomerAuthStatus.EXPIRED == status;
  }

  public boolean hasLoggedOut() {
    return CustomerAuthStatus.LOGGED_OUT == status;
  }

  public boolean isNotFound() {
    return CustomerAuthStatus.NOT_FOUND == status;
  }

  public CustomerAuthStatus getStatus() {
    return status;
  }

  private boolean isExpired(final CustomerAuthEntity customerAuthEntity) {
    final ZonedDateTime now = DateTimeProvider.currentProgramTime();
    return customerAuthEntity != null && (customerAuthEntity.getExpiresAt().isBefore(now)
        || customerAuthEntity.getExpiresAt().isEqual(now));
  }

  private boolean isLoggedOut(final CustomerAuthEntity customerAuthEntity) {
    return customerAuthEntity != null && customerAuthEntity.getLogoutAt() != null;
  }

}