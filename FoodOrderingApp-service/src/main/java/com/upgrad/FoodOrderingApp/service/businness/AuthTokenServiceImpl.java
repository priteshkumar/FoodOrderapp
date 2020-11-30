package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.common.DateTimeProvider;
import com.upgrad.FoodOrderingApp.service.dao.CustomerAuthDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
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

  /*
  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void invalidateToken(final String accessToken){

    final UserAuthTokenEntity userAuthToken = userAuthDao.findToken(accessToken);
    final UserAuthTokenVerifier tokenVerifier = new UserAuthTokenVerifier(userAuthToken);
    if (tokenVerifier.isNotFound()) {
      throw new AuthorizationFailedException(UserErrorCode.USR_005);
    }
    if (tokenVerifier.hasExpired()) {
      throw new AuthorizationFailedException(UserErrorCode.USR_006);
    }

    userAuthToken.setLogoutAt(DateTimeProvider.currentProgramTime());
    userAuthDao.update(userAuthToken);
  }*/

  /*
  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public UserAuthTokenEntity validateToken(@NotNull String accessToken)
      throws AuthorizationFailedException {
    final UserAuthTokenEntity userAuthToken = userAuthDao.findToken(accessToken);
    final UserAuthTokenVerifier tokenVerifier = new UserAuthTokenVerifier(userAuthToken);
    if (tokenVerifier.isNotFound() || tokenVerifier.hasLoggedOut()) {
      throw new AuthorizationFailedException(UserErrorCode.USR_005);
    }
    if (tokenVerifier.hasExpired()) {
      throw new AuthorizationFailedException(UserErrorCode.USR_006);
    }
    return userAuthToken;
  }*/

}