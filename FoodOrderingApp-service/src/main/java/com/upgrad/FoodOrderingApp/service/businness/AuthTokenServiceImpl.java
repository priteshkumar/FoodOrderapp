package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.common.DateTimeProvider;
import com.upgrad.FoodOrderingApp.service.dao.CustomerAuthDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link AuthTokenService}.
 */
@Service
public class AuthTokenServiceImpl implements AuthTokenService {

  @Autowired
  private CustomerDao customerDao;

  @Autowired
  private CustomerAuthDao customerAuthDao;

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public CustomerAuthEntity issueToken(final CustomerEntity customerEntity) {

    final ZonedDateTime now = DateTimeProvider.currentProgramTime();
    /*
    final UserAuthTokenEntity userAuthToken = userAuthDao
        .findActiveTokenByUser(userEntity.getId(), now);
    final UserAuthTokenVerifier tokenVerifier = new UserAuthTokenVerifier(userAuthToken);
    if (tokenVerifier.isActive()) {
      return userAuthToken;
    }*/
    final JwtTokenProvider tokenProvider = new JwtTokenProvider(customerEntity.getPassword());
    final ZonedDateTime expiresAt = now.plusHours(8);
    final String authToken = tokenProvider.generateToken(customerEntity.getUuid(), now, expiresAt);

    final CustomerAuthEntity customerAuthEntity = new CustomerAuthEntity();
    customerAuthEntity.setUuid(UUID.randomUUID().toString());
    customerAuthEntity.setCustomer(customerEntity);
    customerAuthEntity.setAccessToken(authToken);
    customerAuthEntity.setLoginAt(now);
    customerAuthEntity.setExpiresAt(expiresAt);
    return customerAuthDao.create(customerAuthEntity);
  }


  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public CustomerAuthEntity invalidateToken(final String accessToken)
      throws AuthorizationFailedException {

    final CustomerAuthEntity customerAuthEntity = customerAuthDao.findToken(accessToken);
    final CustomerAuthVerifier customerAuthVerifier = new CustomerAuthVerifier(customerAuthEntity);
    if (customerAuthVerifier.isNotFound()) {
      throw new AuthorizationFailedException("ATHR-001", "Customer is not Logged in.");
    }
    if (customerAuthVerifier.hasExpired()) {
      throw new AuthorizationFailedException("ATHR-003",
          "Your session is expired. Log in again to access this endpoint.");
    }
    if (customerAuthVerifier.hasLoggedOut()) {
      throw new AuthorizationFailedException("ATHR-002",
          "Customer is logged out. Log in again to access this endpoint.");
    }
    customerAuthEntity.setLogoutAt(DateTimeProvider.currentProgramTime());
    return customerAuthDao.update(customerAuthEntity);
  }


  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public CustomerAuthEntity validateToken(@NotNull String accessToken)
      throws AuthorizationFailedException {
    final CustomerAuthEntity customerAuthEntity = customerAuthDao.findToken(accessToken);
    final CustomerAuthVerifier tokenVerifier = new CustomerAuthVerifier(customerAuthEntity);
    if (tokenVerifier.isNotFound()) {
      throw new AuthorizationFailedException("ATHR-001", "Customer is not Logged in.");
    }
    if (tokenVerifier.hasLoggedOut()) {
      throw new AuthorizationFailedException("ATHR-002",
          "Customer is logged out. Log in again to access this endpoint.");
    }
    if (tokenVerifier.hasExpired()) {
      throw new AuthorizationFailedException("ATHR-003",
          "(Your session is expired. Log in again to access this endpoint.");
    }
    return customerAuthEntity;
  }

}