package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {

  @Autowired
  private CustomerDao customerDao;

  @Autowired
  private PasswordCryptographyProvider passwordCryptographyProvider;

  /*@Override
  public SearchResult<CustomerEntity> findUsers(int page, int limit) {
    return customerDao.findUsers(page, limit);
  }*/

  /*@Override
  public SearchResult<CustomerEntity> findUsers(UserStatus userStatus, int page, int limit) {
    return customerDao.findUsers(userStatus, page, limit);
  }*/
  /*
  @Override
  @Transactional(propagation = Propagation.SUPPORTS)
  public CustomerEntity findCustomerByEmail(final String emailAddress) {

    final CustomerEntity CustomerEntity = customerDao.findByEmail(emailAddress);
    if (CustomerEntity == null) {
      //throw new EntityNotFoundException(UserErrorCode.USR_002, emailAddress);
    }
    return CustomerEntity;
  }*/

  @Override
  @Transactional(propagation = Propagation.SUPPORTS)
  public CustomerEntity findCustomerByUuid(final String userUuid) {

    final CustomerEntity CustomerEntity = customerDao.findByUUID(userUuid);
    if (CustomerEntity == null) {
      //throw new EntityNotFoundException(UserErrorCode.USR_001, userUuid);
    }
    return CustomerEntity;
  }

  /*@Override
  @Transactional(propagation = Propagation.REQUIRED)
  public CustomerEntity createUser(final CustomerEntity newUser, final Integer roleUuid)
      throws ApplicationException {

    final CustomerEntity existingUser = customerDao.findByEmail(newUser.getEmail());
    if (existingUser != null) {
      throw new ApplicationException(UserErrorCode.USR_009, newUser.getEmail());
    }
    encryptPassword(newUser);
    newUser.setSubscriptionsConsent(true);
    newUser.setRole(roleService.findRoleByUuid(roleUuid));

    return customerDao.create(newUser);
  }*/

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
    if (!verifyPasswordStrength(newCustomer)) {
      throw new SignUpRestrictedException("SGR-004", "Weak password!");
    }
    encryptPassword(newCustomer);
    return customerDao.create(newCustomer);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void updateCustomer(final String userUuid, final CustomerEntity updatedUser) {

    final CustomerEntity existingUser = customerDao.findByUUID(userUuid);
    if (existingUser == null) {
      //throw new EntityNotFoundException(UserErrorCode.USR_001, userUuid);
    }

    /*if (UserStatus.DELETED == UserStatus.valueOf(existingUser.getStatus())) {
      throw new ApplicationException(UserErrorCode.USR_012, userUuid);
    }*/

    /*if (!existingUser.getEmail().equalsIgnoreCase(updatedUser.getEmail())
        && customerDao.findByEmail(updatedUser.getEmail()) != null) {
      //throw new ApplicationException(UserErrorCode.USR_009, updatedUser.getEmail());
    }*/

    if (StringUtils.isNotEmpty(updatedUser.getFirstName())) {
      existingUser.setFirstName(updatedUser.getFirstName());
    }
    if (StringUtils.isNotEmpty(updatedUser.getLastName())) {
      existingUser.setLastName(updatedUser.getLastName());
    }
    if (StringUtils.isNotEmpty(updatedUser.getEmail())) {
      existingUser.setEmail(updatedUser.getEmail());
    }
    if (StringUtils.isNotEmpty(updatedUser.getContact_number())) {
      existingUser.setContact_number(updatedUser.getContact_number());
    }
    //customerDao.update(existingUser);
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
    if(Pattern.matches(digitPattern,contactNumber)){
      return true;
    }
    return false;
  }

  /*
  if the password provided by the customer is weak, i.e.,
  it doesn’t have at least eight characters and does not contain at least one digit,
  one uppercase letter, and one of the following characters [#@$%&*!^]
   */
  private boolean verifyPasswordStrength(final CustomerEntity newCustomer) {
    String password = newCustomer.getPassword();
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