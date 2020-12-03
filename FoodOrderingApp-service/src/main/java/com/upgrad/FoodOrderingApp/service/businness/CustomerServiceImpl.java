package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import com.upgrad.FoodOrderingApp.service.exception.UpdateCustomerException;
import java.util.regex.Pattern;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {

  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  private AuthTokenService authTokenService;

  @Autowired
  private CustomerDao customerDao;

  @Autowired
  private PasswordCryptographyProvider passwordCryptographyProvider;

  @Override
  @Transactional(propagation = Propagation.SUPPORTS)
  public CustomerEntity findCustomerByUuid(final String userUuid) {

    final CustomerEntity CustomerEntity = customerDao.findByUUID(userUuid);
    if (CustomerEntity == null) {
      //throw new EntityNotFoundException(UserErrorCode.USR_001, userUuid);
    }
    return CustomerEntity;
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS)
  public CustomerEntity findCustomerByContactNumber(final String contactNumber) {

    final CustomerEntity CustomerEntity = customerDao.findByContactNumber(contactNumber);
    if (CustomerEntity == null) {
      //throw new EntityNotFoundException(UserErrorCode.USR_001, userUuid);
    }
    return CustomerEntity;
  }

  @Override
  public CustomerEntity getCustomer(@NotNull String accessToken)
      throws AuthorizationFailedException {
    CustomerAuthEntity customerAuthEntity = authTokenService.validateToken(accessToken);
    return customerAuthEntity.getCustomer();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public CustomerEntity saveCustomer(final CustomerEntity newCustomer)
      throws SignUpRestrictedException {
    if (!verifyCustomerData(newCustomer)) {
      throw new SignUpRestrictedException("SGR-005",
          "Except last name all fields should be filled");
    }
    final CustomerEntity customerEntity =
        customerDao.findByContactNumber(newCustomer.getContact_number());
    if (customerEntity != null) {
      throw new SignUpRestrictedException("SGR-001",
          "This contact number is already registered! Try other contact number.");
    }
    if (!verifyEmail(newCustomer)) {
      throw new SignUpRestrictedException("SGR-002", "Invalid email-id format!");
    }
    if (!verifyContactNumber(newCustomer)) {
      throw new SignUpRestrictedException("SGR-003", "Invalid contact number!");
    }
    if (!verifyPasswordStrength(newCustomer.getPassword())) {
      throw new SignUpRestrictedException("SGR-004", "Weak password!");
    }
    encryptPassword(newCustomer);
    return customerDao.create(newCustomer);
  }

  @Override
  public CustomerAuthEntity authenticate(final String contactNumber, final String password)
      throws AuthenticationFailedException {
    return authenticationService.authenticate(contactNumber, password);
  }

  @Override
  public CustomerAuthEntity logout(final String accessToken)
      throws AuthorizationFailedException {
    return authenticationService.logout(accessToken);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public CustomerEntity updateCustomer(final CustomerEntity updatedCustomer) {
    return customerDao.updateCustomer(updatedCustomer);
  }

  @Override
  public CustomerEntity updateCustomerPassword(@NotNull String oldPassword,
      @NotNull String newPassword, @NotNull CustomerEntity customerEntity)
      throws UpdateCustomerException {
    if(!verifyPasswordStrength(newPassword)){
      throw new UpdateCustomerException("UCR-001","Weak password!");
    }
    if(!customerEntity.getPassword().equals(oldPassword)){
      throw new UpdateCustomerException("UCR-004","Incorrect old password!");
    }
    customerEntity.setPassword(newPassword);
    return customerDao.updateCustomer(customerEntity);
  }

  /*
  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void changeUserStatus(final String userUuid, final UserStatus newUserStatus)
      throws ApplicationException {

    final CustomerEntity existingUser = customerDao.findByUUID(userUuid);
    if (existingUser == null) {
      throw new EntityNotFoundException(UserErrorCode.USR_001, userUuid);
    }

    if (UserStatus.valueOf(existingUser.getStatus()) != newUserStatus) {
      existingUser.setStatus(newUserStatus.name());
      customerDao.update(existingUser);
    }
  }*/

  @Override
  public void deleteCustomer(final String userUuid) {

    final CustomerEntity existingUser = customerDao.findByUUID(userUuid);
    if (existingUser == null) {
      //throw new EntityNotFoundException(UserErrorCode.USR_001, userUuid);
    }

    //existingUser.setStatus(UserStatus.DELETED.name());
    //customerDao.update(existingUser);
  }

  /*
  If any field other than last name is empty,
   throw ‘SignUpRestrictedException’ with the message code (SGR-005) and message
  (Except last name all fields should be filled) and their corresponding HTTP status.
   */

  private boolean verifyCustomerData(final CustomerEntity newCustomer) {
    if (StringUtils.isEmpty(newCustomer.getFirstName()) || StringUtils
        .isEmpty(newCustomer.getEmail()) || StringUtils.isEmpty(newCustomer.getPassword())
        || StringUtils.isEmpty(newCustomer.getContact_number())) {
      return false;
    }
    return true;
  }

  /*
  If the email ID provided by the customer is not in the correct format,
  4 i.e., not in the format of xxx@xx.xx
    (here, x is a variable and can be a number or a letter if the alphabet),
      throw ‘SignUpRestrictedException’ with the message code
      (SGR-002) and message (Invalid email-id format!).
 */
  private boolean verifyEmail(final CustomerEntity newCustomer) {
    String email = newCustomer.getEmail();
    email = email.trim();
    String emailPattern = "[a-zA-Z0-9]+[@]{1}[a-zA-Z0-9]+[.]{1}[a-zA-Z0-9]+";
    if (Pattern.matches(emailPattern, email)) {
      return true;
    }
    return false;
  }

  /*
  If the contact number provided by the customer is not in correct format, i.e.,
   it does not contain only numbers and has more or less than 10 digits,
    throw ‘SignUpRestrictedException’ with the message code (SGR-003) and
     message (Invalid contact number!) with the corresponding HTTP status.
   */
  private boolean verifyContactNumber(final CustomerEntity newCustomer) {
    String contactNumber = newCustomer.getContact_number();
    String digitPattern = "^[0-9]{10}$";
    if (Pattern.matches(digitPattern, contactNumber)) {
      return true;
    }
    return false;
  }

  /*
  if the password provided by the customer is weak, i.e.,
  it doesn’t have at least eight characters and does not contain at least one digit,
  one uppercase letter, and one of the following characters [#@$%&*!^]
   */
  private boolean verifyPasswordStrength(String password) {
    password = password.trim();
    String letterPattern = ".*[A-Z]+.*";
    String digitPattern = ".*[0-9]+.*";
    String otherCharPattern = ".*[#@$%*!^].*";
    if (password.length() >= 8 && Pattern.matches(letterPattern, password) && Pattern
        .matches(digitPattern, password)
        && Pattern.matches(otherCharPattern, password)) {
      return true;
    }
    return false;
  }

  private void encryptPassword(final CustomerEntity newUser) {

    String password = newUser.getPassword();
    if (password == null) {
      password = "foodapp@123";
    }

    final String[] encryptedData = passwordCryptographyProvider.encrypt(password);
    newUser.setSalt(encryptedData[0]);
    newUser.setPassword(encryptedData[1]);
  }

}