package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link AuthenticationService}.
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

  @Autowired
  private PasswordCryptographyProvider passwordCryptographyProvider;

  @Autowired
  private CustomerService customerService;

  @Autowired
  private AuthTokenService authTokenService;

  @Autowired
  private CustomerDao customerDao;

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public CustomerAuthEntity authenticate(final String contactNumber, final String password)
      throws AuthenticationFailedException {

    if (StringUtils.isEmpty(contactNumber) || StringUtils.isEmpty(password)) {
      throw new AuthenticationFailedException("ATH-003", "Incorrect format of decoded customer "
          + "name and password");
    }

    CustomerEntity customerEntity;
    customerEntity = customerService.findCustomerByContactNumber(contactNumber);
    if (customerEntity == null) {
      throw new AuthenticationFailedException("ATH-001",
          "This contact number has not been registered!");
    }

    final String encryptedPassword = passwordCryptographyProvider
        .encrypt(password, customerEntity.getSalt());
    if (!customerEntity.getPassword().equals(encryptedPassword)) {
      throw new AuthenticationFailedException("ATH-002", "Invalid Credentials");
    }

    CustomerAuthEntity customerAuthEntity = authTokenService.issueToken(customerEntity);
    return customerAuthEntity;
  }

  @Override
  public CustomerAuthEntity logout(@NotNull String accessToken)
      throws AuthorizationFailedException {
    return authTokenService.invalidateToken(accessToken);
  }

}
